package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhuwenwen
 * @date 2019/9/1 13:43
 **/
@Controller
public class LoginController {

    @GetMapping(value = {"doLogin",""})
    @SkipLogon
    public String login() {
        return "login";
    }
}
