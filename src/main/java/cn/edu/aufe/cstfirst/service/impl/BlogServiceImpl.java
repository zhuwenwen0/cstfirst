
package cn.edu.aufe.cstfirst.service.impl;

import cn.edu.aufe.cstfirst.cache.RedisCacheManageConfiguration1;
import cn.edu.aufe.cstfirst.domian.Blog;
import cn.edu.aufe.cstfirst.domian.BlogExample;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.mapper.BlogMapper;
import cn.edu.aufe.cstfirst.mapper.UserMapper;
import cn.edu.aufe.cstfirst.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author zhuwenwen
 * @date 2018/9/16 13:33
 */
@Service
public class BlogServiceImpl implements BlogService {
    private Logger logger= LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     * 添加博客
     *
     * @param blog 博客
     * @param username 用户名
     * @return 添加结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer saveBlog(Blog blog,String username) {
        int userId = userMapper.selectByUserName(username);
        blog.setUserId(userId);
        blog.setUsername(username);
        return blogMapper.insertSelective(blog);
    }

    /**
     * 根据id查找博客
     *
     * @param blogId 博客id
     * @return 博客
     */
    @Override
    @Cacheable(cacheNames = "Blog",key = "#blogId",cacheManager = RedisCacheManageConfiguration1.REDIS_CACHE_MANAGER)
    public Blog getBlogById(Integer blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    /**
     * 根据id删除博客
     *
     * @param blogId 博客id
     * @param username 用户名
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteByBlogId(Integer blogId,String username) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        if (blog.getUsername().equals(username)){
            blog.setState(0);
            BlogExample blogExample = getBlogExample();
            blogExample.createCriteria().andIdEqualTo(blogId);
            try {
                return blogMapper.updateByExample(blog,blogExample);
            }catch (Exception ex){
                logger.error("修改博客失败,{}",ex);
                throw new BlogException("服务器繁忙，请稍后重试",-1);
            }
        }else {
            throw new BlogException("亲 ，请不要删除他人博客，节操不能掉",-1);
        }
    }

    /**
     * 根据blogId修改博客
     *
     * @param blog 博客
     * @param username 用户名
     * @return 影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateBlogByBlogId(Blog blog, String username) {
        if (blog.getUsername().equals(username)){
            BlogExample blogExample = getBlogExample();
            blogExample.createCriteria().andIdEqualTo(blog.getId());
            try {
                return blogMapper.updateByExample(blog,blogExample);
            }catch (Exception ex){
                logger.error("修改博客失败:{}",ex);
                throw new BlogException("服务器繁忙，请稍后重试",-1);
            }
        }else {
            throw new BlogException("亲 ，请不要修改他人博客，否则会有人拿着刀在来的路上",-1);
        }
    }

    /**
     * 查询出所有博客
     *
     * @return 博客列表
     */
    @Cacheable(cacheNames = "BlogList",key = "'blogList'",cacheManager = RedisCacheManageConfiguration1.REDIS_CACHE_MANAGER)
    @Override
    public List<Blog> listAll(String blogName) {
        BlogExample blogExample=new BlogExample();
        if (StringUtils.isNotBlank(blogName)){
            blogExample.createCriteria().andTitleLike("%"+blogName+"%");
        }
       return blogMapper.selectByExample(blogExample);
    }

    private BlogExample getBlogExample(){
        return new BlogExample();
    }


}
