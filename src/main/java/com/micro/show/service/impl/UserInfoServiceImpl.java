package com.micro.show.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.show.entity.UserInfo;
import com.micro.show.mapper.UserInfoMapper;
import com.micro.show.service.IUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
