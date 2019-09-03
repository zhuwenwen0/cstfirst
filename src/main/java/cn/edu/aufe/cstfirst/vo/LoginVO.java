package cn.edu.aufe.cstfirst.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhuwenwen
 * @date 2019/9/3 21:44
 **/
@Getter
@Setter
public class LoginVO {

    private String username;

    private String password;

    private String validateCode;

    private Boolean rememberMe;
}
