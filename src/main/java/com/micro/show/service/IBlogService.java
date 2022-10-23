package com.micro.show.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.micro.show.dto.Result;
import com.micro.show.entity.Blog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muxiaoling
 * @since 2021-12-22
 */
public interface IBlogService extends IService<Blog> {

    Result queryHotBlog(Integer current);

    Result queryBlogById(Long id);

    Result likeBlog(Long id);

    Result queryBlogLikes(Long id);

    Result saveBlog(Blog blog);

    Result queryBlogOfFollow(Long max, Integer offset);

}
