package cn.edu.aufe.cstfirst.mapper;

import cn.edu.aufe.cstfirst.domian.Comment;
import cn.edu.aufe.cstfirst.domian.CommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    /**
     * 查询所有的父评论
     *
     * @param blogId 博客id
     * @return 父评论
     */
    List<Comment> selectParentByBlogId(@Param("blogId") Integer blogId);

    List<Comment> selectChildrenByParentId(@Param("parentIds") List<Integer> parentIds);

    void deleteByParentId(@Param("parentId") Integer parentId);
}