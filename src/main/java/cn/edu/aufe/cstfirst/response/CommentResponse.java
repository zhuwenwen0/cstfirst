
package cn.edu.aufe.cstfirst.response;

import cn.edu.aufe.cstfirst.domian.Comment;
import lombok.Data;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/10/20 17:01
 */
@Data
public class CommentResponse {

    /**
     * 父评论
     */
    private Comment parentComment;

    /**
     * 子评论
     */
    private List<Comment> childrenComment;
}
