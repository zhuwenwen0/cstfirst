
package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.AuthenticationParam;
import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import cn.edu.aufe.cstfirst.domian.Blog;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author zhuwenwen
 * @date 2018/9/16 13:38
 */
@RestController
@RequestMapping("api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加博客
     *
     * @param blog     博客
     * @param username 用户名
     * @return 添加结果
     */
    @PostMapping("save")
    public Result add(@RequestBody Blog blog, @AuthenticationParam String username) {
        if (StringUtils.isBlank(blog.getTitle())){
            throw new BlogException("博客标题不能为空",-1);
        }
        if (StringUtils.isBlank(blog.getTags())){
            throw new BlogException("请添加博客标签",-1);
        }
        if (StringUtils.isBlank(blog.getContent())){
            throw new BlogException("请填写博客内容",-1);
        }
        blog.setCrateTime(LocalDateTime.now());
        blog.setState(1);
        blog.setUpdateTime(LocalDateTime.now());
        return Result.success(blogService.saveBlog(blog, username));
    }

    /**
     * 根据blogId查询博客
     *
     * @param blogId 博客id
     * @return 博客
     */
    @SkipLogon
    @GetMapping("get")
    public Result get(@RequestParam("blogId") Integer blogId) {
        return Result.success(blogService.getBlogById(blogId));
    }


    /**
     * 根据博客id删除博客
     *
     * @param blogId   博客id
     * @param username 用户名
     * @return 删除结果
     */
    @PostMapping("delete")
    public Result deleteByBlogId(@RequestParam("blogId") Integer blogId, @AuthenticationParam String username) {
        return Result.success(blogService.deleteByBlogId(blogId, username));
    }

    /**
     * 修改博客
     *
     * @param blog 博客
     * @param username 用户名
     * @return 修改结果
     */
    @PostMapping("update")
    public Result updateBlog(@RequestBody Blog blog, @AuthenticationParam String username) {
        if (blog.getId() != null && blog.getId()<0){
            throw new BlogException("当前博客不存在",-1);
        }
        if (StringUtils.isBlank(blog.getTitle())){
            throw new BlogException("博客标题不能为空",-1);
        }
        if (StringUtils.isBlank(blog.getContent())){
            throw new BlogException("博客内容不能为空",-1);
        }
        if (StringUtils.isBlank(blog.getTags())){
            throw new BlogException("博客标签不能为空",-1);
        }
        return Result.success(blogService.updateBlogByBlogId(blog,username));
    }

    /**
     * 查询所有博客
     *
     * @return 博客列表
     */
    @SkipLogon
    @GetMapping("list")
    public Result listAll(@RequestParam(value = "blogName",required = false) String blogName){
        return Result.success(blogService.listAll(blogName));
    }

}
