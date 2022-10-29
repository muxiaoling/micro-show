package com.micro.show;

import com.micro.show.entity.Shop;
import com.micro.show.service.IShopService;
import com.micro.show.utils.BloomFilterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author muxiaoling
 * @date 2022/10/21 10:32
 */
@SpringBootTest
class BloomFilterTest {
    @Resource
    private IShopService shopService;
    @Test
    void bloomFilter() {
        List<Long> list = shopService.list().stream().map(Shop::getId).collect(Collectors.toList());
        list.forEach(o -> BloomFilterUtil.addElement(o,  "test"));
    }
}
