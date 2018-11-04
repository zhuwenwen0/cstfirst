
package cn.edu.aufe.cstfirst.handler;

/**
 * 定义错误编号
 *
 * @author zhuwenwen
 * @date 2018/8/17 9:25
 */
public  enum BlogEnum {
    /**
     * 返回枚举值
     */
    SUCCESS(200,"响应成功"),
    ERROR(0,"响应失败"),
    FAILURE_UNKNOWN(-1,"系统异常"),
    /**
     *  900000       : HTTP状态码错误
     *  900001-900999: HTTP状态码详细错误
     *
     *  例如: 500错误，返回900500。
     */
    FAILURE_HTTP(900000,"HTTP状态码错误"),
    FAILURE(901000,"自定义错误"),
    FAILURE_VALIDATION(901001,"参数验证错误"),
    FAILURE_UNAUTHORIZED(901002,"未登录错误"),
    FAILURE_DB(901100,"数据库错误"),
    FAILURE_DB_MYSQL(901101,"MySQL 错误"),
    FAILURE_DB_SQLSERVER(901102,"SQL Server 错误"),
    FAILURE_SERVLET(901200,"Servlet错误")
    ;
    private final int code;
    private final String msg;

    BlogEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
