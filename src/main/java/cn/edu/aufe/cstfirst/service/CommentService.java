
package cn.edu.aufe.cstfirst.service;

import cn.edu.aufe.cstfirst.domian.Comment;
import cn.edu.aufe.cstfirst.response.CommentResponse;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/9/16 19:01
 */
public interface CommentService {

    /**
     * 插入一条评论
     *
     * @param comment 评论
     * @return 添加结果
     */
    Integer insertComment(Comment comment);

    /**
     * 查询一个博客下面所有的评论
     *
     * @param blogId 博客id
     * @return 评论列表
     */
    List<CommentResponse> listComment(Integer blogId);

    /**
     * 根据id删除评论
     *
     * @param commentId 评论id
     * @return 操作结果
     */
    Integer deleteCommentById(Integer commentId);
}
