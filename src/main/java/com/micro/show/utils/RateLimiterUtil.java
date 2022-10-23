package com.micro.show.utils;

/**
 * @author muxiaoling
 * @date 2022/10/22 23:23
 */

import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流算法
 */
public class RateLimiterUtil {
    /**
     * 根据业务场景设置不同的限流器
     */
    private static Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

    /**
     * 如果不存在对应限流器则根据传入的令牌生成速度,名称设置限流器
     * 随后尝试获取令牌
     */
    public static boolean tryAcquire(String limiterName, double permitsPerSecond) {
        RateLimiter rateLimiter;
        if (!rateLimiterMap.containsKey(limiterName)) {
            rateLimiter = RateLimiter.create(permitsPerSecond);
            rateLimiterMap.put(limiterName, rateLimiter);
        } else {
            rateLimiter = rateLimiterMap.get(limiterName);
        }
        return rateLimiter.tryAcquire();
    }

}
