package com.micro.show.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.micro.show.entity.SeckillVoucher;

/**
 * <p>
 * 秒杀优惠券表，与优惠券是一对一关系 Mapper 接口
 * </p>
 *
 * @author muxiaoling
 * @since 2022-01-04
 */
public interface SeckillVoucherMapper extends BaseMapper<SeckillVoucher> {
    default void updateStock(Long voucherId) {
        new LambdaUpdateChainWrapper<SeckillVoucher>(this)
                .eq(SeckillVoucher::getVoucherId, voucherId)
                .gt(SeckillVoucher::getStock, 0)
                .setSql("stock = stock - 1")
                .update();
    }
}
