package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.Result;
import com.micro.show.entity.ShopType;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IShopTypeService extends IService<ShopType> {
    /**
     * 查询商户类型
     * @return 商户类型集合
     */
    Result queryTypeList();
}
