
package cn.edu.aufe.cstfirst.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/9/30 16:59
 */
@Data
public class CommentVo {
    private String fullName;

    private Integer id;

    private Integer userId;

    private Integer blogId;

    private String comment;

    private Integer parentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer state;

    private String username;

    private List<CommentVo> childComment;
}
