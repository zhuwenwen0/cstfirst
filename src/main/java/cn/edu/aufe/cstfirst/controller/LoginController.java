package cn.edu.aufe.cstfirst.controller;

import cn.edu.aufe.cstfirst.common.annotation.SkipLogon;
import cn.edu.aufe.cstfirst.domian.User;
import cn.edu.aufe.cstfirst.handler.BlogEnum;
import cn.edu.aufe.cstfirst.handler.BlogException;
import cn.edu.aufe.cstfirst.handler.Result;
import cn.edu.aufe.cstfirst.service.UserService;
import cn.edu.aufe.cstfirst.util.JwtUtil;
import cn.edu.aufe.cstfirst.util.LocalDateTimeUtil;
import cn.edu.aufe.cstfirst.vo.LoginVO;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhuwenwen
 * @date 2019/9/1 13:43
 **/
@Controller
public class LoginController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private UserService userService;

    @GetMapping(value = {"doLogin", ""})
    @SkipLogon
    public String doLogin(Model model) {
        model.addAttribute("captchaEnabled", true);
        model.addAttribute("captchaType", "math");
        return "login";
    }

    @GetMapping("captcha/captchaImage")
    @SkipLogon
    @ResponseBody
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type)) {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type)) {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            //把验证码放入到session中
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 登录接口
     *
     * @param loginVO loginVo请求参数
     * @param request http请求
     * @return {@link Result}
     */
    @PostMapping("login")
    @SkipLogon
    @ResponseBody
    public Result login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        if (StringUtils.isBlank(loginVO.getValidateCode())) {
            throw new BlogException(BlogEnum.VALIDATE_CODE_NOT_EXISTS);
        }
        String code = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        if (!StringUtils.equals(loginVO.getValidateCode(), code)) {
            throw new BlogException(BlogEnum.VALIDATE_CODE_ERROR);
        }
        // 验证用户名和密码，然后返回token和有效期
        User user = userService.logon(loginVO.getUsername(), loginVO.getPassword());
        if (user == null) {
            throw new BlogException(BlogEnum.USER_ONT_EXISTS);
        }
        LocalDateTime tokenExpire = LocalDateTimeUtil.plusMinute(30);
        Date expire = LocalDateTimeUtil.localDateTime2Date(tokenExpire);
        String token = JwtUtil.encryptKey(expire, user.getUsername(), user.getPassword());
        return Result.success(token);
    }

    @GetMapping("index")
    @SkipLogon
    public String index() {
        return "index";
    }
}
