
package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.AuthenticationParam;
import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import cn.edu.aufe.cstfirst.domian.Blog;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("delete")
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
    @PutMapping("update")
    public Result updateBlog(@RequestBody Blog blog, @AuthenticationParam String username) {
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
