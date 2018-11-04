
package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import cn.edu.aufe.cstfirst.common.handler.Constant;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.request.LogonRequest;
import cn.edu.aufe.cstfirst.request.TokenRequest;
import cn.edu.aufe.cstfirst.service.UserService;
import cn.edu.aufe.cstfirst.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author zhuwenwen
 * @date 2018/9/8 13:09
 */
@RestController
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 登录接口
     *
     * @param request 请求信息
     * @return token信息
     */
    @SkipLogon
    @PostMapping("logon")
    public Result login(@RequestBody  LogonRequest request){
        if (StringUtils.isBlank(request.getUsername())){
            throw new BlogException("用户名不能为空",-1);
        }
        if (StringUtils.isBlank(request.getPassword())){
            throw new BlogException("请填写登录密码",-1);
        }
        if (userService.logon(request.getUsername(),request.getPassword()) == 1){
            long currentTimeMillis = System.currentTimeMillis();
            //设置token失效时间
            currentTimeMillis=currentTimeMillis+30*60*1000;
            Date date=new Date(currentTimeMillis);
            return Result.success(JwtUtil.encryptKey(date,request.getUsername(),request.getPassword()));
        }else {
            throw new BlogException("登录失败，请重新登录",-1);
        }
    }

    /**
     * 刷新token接口
     *
     * @param tokenRequest 请求信息
     * @return 新的token
     */
    @PostMapping("refreshToke")
    public Result refreshToken(@RequestBody TokenRequest tokenRequest){
        if (StringUtils.isBlank(tokenRequest.getToken())){
            throw new BlogException("身份信息不能为空",-1);
        }
        Claims claims = JwtUtil.decryptKey(tokenRequest.getToken());
        long currentTimeMillis = System.currentTimeMillis();
        currentTimeMillis=currentTimeMillis+30*60*1000;
        Date date=new Date(currentTimeMillis);
        String username = (String) claims.get(Constant.USERNAME);
        String password = (String) claims.get(Constant.PASSWORD);
        return Result.success(JwtUtil.encryptKey(date,username,password));
    }

}
