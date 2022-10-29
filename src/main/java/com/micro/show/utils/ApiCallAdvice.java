package com.micro.show.utils;

import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author muxiaoling
 * @date 2022/10/18 8:30
 */

/**
 * 接口调用情况监控
 * 1、单个接口一天内调用次数
 * 2、如果抛出异常，记录异常信息及发生时间
 * 3、对单个Ip进行限流，每天对每个接口的调用次数有限制
 */
@Aspect
@Component
public class ApiCallAdvice {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String FORMAT_PATTERN_DAY = "yyyy-MM-dd";
    private static final String FORMAT_PATTERN_MILLS = "yyyy-MM-dd HH:mm:ss:SSS";


    /**
     * 真正执行业务操作前先进行限流的验证
     * 限制维度为：一天内单个IP的访问次数
     * key = URI + IP + date（精确到天）
     * value = 调用次数
     */
    @Before("execution(* com.micro.show.controller.BlogController.saveBlog(..))")
    public void before() {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        HttpServletRequest request = attributes.getRequest();

        String uri = request.getRequestURI();
        String date = dateFormat(FORMAT_PATTERN_DAY);
        String ip = IPUtil.getIpAddress(request);

        if (StrUtil.isEmpty(ip)) {
            throw new RuntimeException("IP不能为空。");
        }
        //URI + IP + 日期 构成以天为维度的key
        String ipKey = uri + "_" + ip + "_" + date;
        if (stringRedisTemplate.hasKey(ipKey)) {
            if (Integer.parseInt(stringRedisTemplate.opsForValue().get(ipKey)) > 20) {
                throw new RuntimeException("发表失败，已超过发表次数。");
            }
            stringRedisTemplate.opsForValue().increment(ipKey, 1);
        } else {
            stringRedisTemplate.opsForValue().set(ipKey, "1", 1L, TimeUnit.DAYS);
        }
    }

    /**
     * 如果有返回结果，代表一次调用，则对应接口的调用次数加一，统计维度为天
     * （Redis使用Hash结构）
     * key = URI
     * key = date （精确到天）
     * value = 调用次数
     */
    @AfterReturning("execution(* com.micro.show.controller.ShopController.saveShop(..))")
    public void afterReturning() {
        //接收请求，记录请求内容
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求request
        HttpServletRequest request = attributes.getRequest();

        String uri = request.getRequestURI();
        String date = dateFormat(FORMAT_PATTERN_DAY);
        if (stringRedisTemplate.hasKey(uri)) {
            stringRedisTemplate.boundHashOps(uri).increment(date, 1);
        } else {
            stringRedisTemplate.boundHashOps(uri).put(date, "1");
        }
    }

    /**
     * 如果调用抛出异常，则缓存异常信息（Redis使用Hash结构）
     * key = URI + “_exception”
     * key = time (精确到毫秒的时间)
     * value = exception 异常信息
     *
     * @param ex 异常信息
     */
    @AfterThrowing(value = "execution(* com.micro.show.controller..*.*(..))", throwing = "ex")
    public void afterThrowing(Exception ex) {
        //接收请求，记录请求内容
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求request
        HttpServletRequest request = attributes.getRequest();

        String uri = request.getRequestURI() + "_exception";
        String time = dateFormat(FORMAT_PATTERN_MILLS);
        String exception = ex.getMessage();

        stringRedisTemplate.boundHashOps(uri).put(time, exception);
    }

    private String dateFormat(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(new Date());
    }


}
