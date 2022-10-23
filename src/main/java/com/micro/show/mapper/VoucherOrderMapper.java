package com.micro.show.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.micro.show.entity.VoucherOrder;

import java.util.Collection;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface VoucherOrderMapper extends BaseMapper<VoucherOrder> {

    /**
     * 获取当前用户在给定订单状态下购买该优惠券的创建的订单数量
     * @param userId     当前用户id
     * @param voucherId  优惠券id
     * @param status     订单状态
     * @return 订单数量
     */
    default int getVoucherOrderCount(Long userId, Long voucherId, Collection<?> status) {
        return this.selectCount(Wrappers.<VoucherOrder>lambdaQuery()
                .eq(VoucherOrder::getVoucherId, voucherId)
                .eq(VoucherOrder::getUserId, userId)
                .in(VoucherOrder::getStatus, status));
    }
}
