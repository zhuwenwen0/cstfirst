
package cn.edu.aufe.cstfirst.handler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author zhuwenwen
 * @date 2018/8/20 18:00
 */
@JsonInclude()
public class Result<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;



    public Integer getCode() {
            return code;
        }

    public void setCode(Integer code) {
            this.code = code;
        }

    public String getMessage() {
            return message;
        }

    public void setMessage(String message) {
            this.message = message;
        }

    public T getData() {
            return data;
        }

    public void setData(T data) {
            this.data = data;
        }

    public static <T> Result<T> success(Integer code, String message , T t) {
        return build(code,message,t);
    }
    public static <T> Result<T> success(T t) {
        return success(BlogEnum.SUCCESS.getCode(), BlogEnum.SUCCESS.getMsg(), t);
    }


    public static Result success() {
        return success(null);
    }

    public static <T> Result<T> error(BlogEnum blogEnum, T t) {
        return error(blogEnum.getCode(), blogEnum.getMsg(),t);
    }

    public static <T> Result<T> error(BlogEnum blogEnum) {
        return error(blogEnum, null);
    }

    public static Result error() {
        return error(BlogEnum.ERROR);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return error(code,message,null);
    }
    public static <T> Result<T> error(Integer code, String message, T t) {
        return build(code,message,t);
    }

    public static <T> Result<T> build(Integer code,String message,T data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }


}
