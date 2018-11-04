
package cn.edu.aufe.cstfirst.service.impl;

import cn.edu.aufe.cstfirst.domian.Comment;
import cn.edu.aufe.cstfirst.domian.CommentExample;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.mapper.CommentMapper;
import cn.edu.aufe.cstfirst.response.CommentResponse;
import cn.edu.aufe.cstfirst.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhuwenwen
 * @date 2018/9/16 19:01
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 添加一条评论
     *
     * @param comment 评论
     * @return 添加结果
     */
    @Override
    public Integer insertComment(Comment comment) {
        return commentMapper.insertSelective(comment);
    }

    /**
     * 查询一个博客下面所有的评论
     *
     * @param blogId 博客id
     * @return 评论列表
     */
    @Override
    public List<CommentResponse> listComment(Integer blogId) {
        List<CommentResponse> listCommentResponse=new ArrayList<>();
        //先获取父评论
        List<Comment> parentComments = commentMapper.selectParentByBlogId(blogId);
        //获取所有父评论的id
        List<Integer> parentCommentIds = parentComments.stream().map(Comment::getParentId).collect(Collectors.toList());
        //获取所有的子评论
        List<Comment> childComments = commentMapper.selectChildrenByParentId(parentCommentIds);
        //组装数据
        for (Comment c:parentComments) {
            List<Comment> childComment = childComments.stream().filter(comment -> comment.getParentId().equals(c.getParentId())).collect(Collectors.toList());
            CommentResponse commentResponse=new CommentResponse();
            commentResponse.setParentComment(c);
            commentResponse.setChildrenComment(childComment);
            listCommentResponse.add(commentResponse);
        }
        return listCommentResponse;
    }

    /**
     * 根据id删除评论
     *
     * @param commentId 评论id
     * @return 删除结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer deleteCommentById(Integer commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment == null){
            throw new BlogException("当前评论不存在",-1);
        }
        CommentExample example=new CommentExample();
        //如果是父评论的话
        if (comment.getParentId() == 0){
            example.createCriteria().andIdEqualTo(commentId);
            comment.setState(0);
            commentMapper.deleteByParentId(commentId);
            return commentMapper.updateByExample(comment,example);
        }else {
            example.createCriteria().andIdEqualTo(commentId);
            comment.setState(0);
            return commentMapper.updateByExample(comment,example);
        }
    }


}
