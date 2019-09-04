
package cn.edu.aufe.cstfirst.service;

import cn.edu.aufe.cstfirst.domian.User;

/**
 * @author zhuwenwen
 * @date 2018/9/8 22:16
 */
public interface UserService {
    /**
     * 用户进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    User logon(String username,String password);

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    User getUserByUsername(String username);

}
