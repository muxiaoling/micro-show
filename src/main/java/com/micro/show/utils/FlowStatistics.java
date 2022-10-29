package com.micro.show.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.micro.show.utils.RedisConstants.TODAY_USER_COUNT;

/**
 * @author muxiaoling
 * @date 2022/10/29 14:11
 */
@Aspect
@Component
@Slf4j
public class FlowStatistics {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("@annotation(com.micro.show.annotation.UserCount)")
    public void annotationAspect() {
    }

    @AfterReturning(value = "annotationAspect()")
    public void doCount() {
        //接收请求，记录请求内容
        ServletRequestAttributes attributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求request
        HttpServletRequest request = attributes.getRequest();

        //获取访问者的Ip
        String ipAddr = IPUtil.getIpAddress(request);
        log.info("访问首页的ip地址：【{}】", ipAddr);

        //将ip存入redis
        stringRedisTemplate.opsForHyperLogLog().add(TODAY_USER_COUNT, ipAddr);
    }

}
