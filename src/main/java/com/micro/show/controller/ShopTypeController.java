package com.micro.show.controller;


import com.micro.show.annotation.UserCount;
import com.micro.show.dto.Result;
import com.micro.show.service.IShopTypeService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.micro.show.utils.RedisConstants.TODAY_USER_COUNT;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author muxiaoling
 */
@RestController
@RequestMapping("/shop-type")
public class ShopTypeController {
    @Resource
    private IShopTypeService shopTypeService;

    @GetMapping("list")
    @UserCount
    public Result queryTypeList() {
//        List<ShopType> typeList = typeService
//                .query().orderByAsc("sort").list();
        //增加了缓存
        return shopTypeService.queryTypeList();
    }
}
