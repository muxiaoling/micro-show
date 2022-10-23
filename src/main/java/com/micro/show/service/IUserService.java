package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.LoginFormDTO;
import com.micro.show.dto.Result;
import com.micro.show.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

    /**
     * 签到
     */
    Result sign();

    /**
     * 获取本月连续签到次数
     */
    Result signCount();

    Result logout(HttpServletRequest request);
}
