package com.micro.show;

import com.micro.show.mapper.SeckillVoucherMapper;
import com.micro.show.utils.RateLimiterUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author muxiaoling
 * @date 2022/10/22 11:08
 */
@SpringBootTest
@Slf4j
public class SeckillVoucherTest {
    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;

    @Test
    void updateStock() {
        seckillVoucherMapper.updateStock(10L);
    }

    @Test
    void rateLimiterTest() {

    }

    private static CountDownLatch countDownLatch = new CountDownLatch(100);
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 2000; i++) {
            new Thread(() -> {
                test();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(success.get() + "----" + fail.get());
    }
    private static volatile AtomicInteger success = new AtomicInteger();
    private static volatile AtomicInteger fail = new AtomicInteger();
    public static void test() {
        if (RateLimiterUtil.tryAcquire("test", 10)) {
            success.getAndIncrement();
            //System.out.println("成功进入");
            log.info("成功进入");
        } else {
            fail.getAndIncrement();
            //System.out.println("失败");
            log.info("失败");
        }
    }
}
