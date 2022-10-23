package com.micro.show.utils;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author muxiaoling
 * @date 2022/10/21 0:11
 */
@Component
public class BloomFilterUtil {
    private static Redisson redisson;

    @Autowired
    public BloomFilterUtil(Redisson redisson){
        BloomFilterUtil.redisson = redisson;
    }
    private BloomFilterUtil (){
    }
    private static volatile RBloomFilter<Long> bloomFilter;

    public static RBloomFilter<Long> getBloomFilter(){
        if (bloomFilter == null) {
            synchronized (BloomFilterUtil.class) {
                if (bloomFilter == null) {
                    bloomFilter = redisson.getBloomFilter("shop_pass_through");
                    bloomFilter.tryInit(100000000,0.04);
                }
            }
        }
        return bloomFilter;
    }

    public static void addElement(Long element) {
        getBloomFilter().add(element);
    }


    public static boolean contains(Long element) {
        return getBloomFilter().contains(element);
    }

}

