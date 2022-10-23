package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.Result;
import com.micro.show.entity.Voucher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IVoucherService extends IService<Voucher> {

    Result queryVoucherOfShop(Long shopId);

    void addSeckillVoucher(Voucher voucher);
}
