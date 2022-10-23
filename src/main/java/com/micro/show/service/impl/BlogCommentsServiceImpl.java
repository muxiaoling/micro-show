package com.micro.show.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.micro.show.entity.BlogComments;
import com.micro.show.mapper.BlogCommentsMapper;
import com.micro.show.service.IBlogCommentsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
