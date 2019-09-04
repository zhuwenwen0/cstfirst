
package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.handler.Constant;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.request.TokenRequest;
import cn.edu.aufe.cstfirst.response.LogonResponse;
import cn.edu.aufe.cstfirst.util.JwtUtil;
import cn.edu.aufe.cstfirst.util.LocalDateTimeUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhuwenwen
 * @date 2018/9/8 13:09
 */
@RestController
public class UserController {


    /**
     * 刷新token接口
     *
     * @param tokenRequest 请求信息
     * @return 新的token
     */
    @PostMapping("refreshToke")
    public Result refreshToken(@RequestBody TokenRequest tokenRequest) {
        if (StringUtils.isBlank(tokenRequest.getToken())) {
            throw new BlogException("身份信息不能为空", -1);
        }
        Claims claims = JwtUtil.decryptKey(tokenRequest.getToken());
        LocalDateTime refresh = LocalDateTimeUtil.plusMinute(30);
        Date expire = LocalDateTimeUtil.localDateTime2Date(refresh);
        String username = (String) claims.get(Constant.USERNAME);
        String password = (String) claims.get(Constant.PASSWORD);

        LogonResponse logonResponse = new LogonResponse();
        logonResponse.setUsername(username);
        logonResponse.setToken(JwtUtil.encryptKey(expire, username, password));
        return Result.success(logonResponse);
    }

}
