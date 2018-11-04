
package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.AuthenticationParam;
import cn.edu.aufe.cstfirst.domian.Comment;
import cn.edu.aufe.cstfirst.domian.User;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.service.CommentService;
import cn.edu.aufe.cstfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuwenwen
 * @date 2018/9/15 22:30
 */
@RestController
@RequestMapping("api/comment")
public class CommentController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping("save")
    public Result add(@RequestBody Comment comment, @AuthenticationParam String username){
        User user = userService.getUserByUsername(username);
        comment.setUsername(username);
        comment.setUserId(user.getId());
        if (comment.getParentId() == null){
            comment.setParentId(0);
        }
        comment.setType(0);
        return Result.success(commentService.insertComment(comment));
    }


    @GetMapping("list")
    public Result listByBlogId(@RequestParam("blogId") Integer blogId){
        if (blogId == null || blogId <0){
            throw new BlogException("获取评论异常",-1);
        }
        return Result.success(commentService.listComment(blogId));
    }

    @PostMapping("delete")
    public Result deleteByCommentId(@RequestParam("commentId")Integer commentId){
        if (commentId == null){
            throw new BlogException("当前评论不存在",-1);
        }
        return Result.success(commentService.deleteCommentById(commentId));
    }
}
