package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.Result;
import com.micro.show.entity.VoucherOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IVoucherOrderService extends IService<VoucherOrder> {

    Result seckillVoucher(Long voucherId);
}
