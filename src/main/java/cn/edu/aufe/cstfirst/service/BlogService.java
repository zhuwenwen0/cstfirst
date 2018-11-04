
package cn.edu.aufe.cstfirst.service;

import cn.edu.aufe.cstfirst.domian.Blog;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/9/16 13:17
 */
public interface BlogService {

    /**
     * 添加博客
     *
     * @param blog 博客
     * @Param username 用户名
     * @return 添加结果
     */
    Integer saveBlog(Blog blog, String username);

    /**
     * 根据id查找博客
     *
     * @param blogId 博客id
     * @return 博客
     */
    Blog getBlogById(Integer blogId);

    /**
     * 根据id删除博客
     *
     * @param blogId 博客id
     * @param username 用户名
     * @return 删除结果
     */
    Integer deleteByBlogId(Integer blogId,String username);

    /**
     * 根据id修改博客
     *
     * @param blog 博客
     * @param username 用户名
     * @return 修改影响行数
     */
    Integer updateBlogByBlogId(Blog blog,String username);


    List<Blog> listAll(String blogName);
}
