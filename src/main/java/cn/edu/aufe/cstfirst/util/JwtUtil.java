
package cn.edu.aufe.cstfirst.util;

import cn.edu.aufe.cstfirst.handler.BlogException;
import io.jsonwebtoken.*;

import java.util.Date;


/**
 * @author zhuwenwen
 * @date 2018/10/20 23:36
 */
public class JwtUtil {

    private static final String PRIVATE_KEY = "cstFirstBlog";

    public static String encryptKey(Date exp,String username,String password) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                .setExpiration(exp)
                //该方法是在JWT中加入值为value的key字段
                .claim("username",username )
                .claim("password",password)
                .compact();
    }

    public static Claims decryptKey(String encryptKey) {
        try {
            //解析JWT字符串中的数据，并进行最基础的验证
            return  Jwts.parser()
                    //SECRET_KEY是加密算法对应的密钥，jwt可以自动判断机密算法
                    .setSigningKey(PRIVATE_KEY)
                    .parseClaimsJws(encryptKey)
                    .getBody();
        }
        //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
        //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        catch (SignatureException | ExpiredJwtException e) {
            throw new BlogException("身份异常，请重新登录",-1);
        }
    }

}
