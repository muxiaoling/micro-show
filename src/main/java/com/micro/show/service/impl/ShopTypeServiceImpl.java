package com.micro.show.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.show.dto.Result;
import com.micro.show.entity.ShopType;
import com.micro.show.mapper.ShopTypeMapper;
import com.micro.show.service.IShopTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.micro.show.utils.RedisConstants.CACHE_SHOP_TYPE_KEY;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {
        //从redis获取商户类型
        String shopType = stringRedisTemplate.opsForValue().get(CACHE_SHOP_TYPE_KEY);
        if (StrUtil.isNotBlank(shopType)) {
            //redis中存在，直接返回
            List<ShopType> shopTypes = JSONUtil.toList(shopType, ShopType.class);
            return Result.ok(shopTypes);
        }
        //redis不存在，走数据库
        List<ShopType> shopTypes = query().orderByAsc("sort").list();
        //数据库不存在，返回错误
        if (shopTypes == null) {
            return Result.fail("商户分类不存在");
        }
        //存入redis
        stringRedisTemplate.opsForValue().set(CACHE_SHOP_TYPE_KEY, JSONUtil.toJsonStr(shopTypes));

        return Result.ok(shopTypes);
    }
}
