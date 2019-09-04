
package cn.edu.aufe.cstfirst.handler;

import lombok.Getter;

/**
 * @author zhuwenwen
 * @date 2018/8/16 20:18
 */
@Getter
public class BlogException extends RuntimeException {
    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 附加数据
     */
    private Object data;

    public BlogException(String errorMsg) {

        super(errorMsg);
        this.code= BlogEnum.ERROR.getCode();
    }

    public BlogException(String errorMsg, Integer code) {
        super(errorMsg);
        this.code = code;
    }

    public BlogException(BlogEnum blogEnum) {
        super(blogEnum.getMsg());
        this.code = blogEnum.getCode();
    }


    public BlogException(Integer code, String errorMsg, Throwable errorCourse) {
        super(errorMsg,errorCourse);
        this.code = code;
    }

    public BlogException(String message, Integer code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

}
