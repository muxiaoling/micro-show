package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.Result;
import com.micro.show.entity.Follow;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IFollowService extends IService<Follow> {

    Result follow(Long followUserId, Boolean isFollow);

    Result isFollow(Long followUserId);

    Result followCommons(Long id);
}
