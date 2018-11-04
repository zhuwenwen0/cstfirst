
package cn.edu.aufe.cstfirst.service.impl;

import cn.edu.aufe.cstfirst.domian.User;
import cn.edu.aufe.cstfirst.domian.UserExample;
import cn.edu.aufe.cstfirst.mapper.UserMapper;
import cn.edu.aufe.cstfirst.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author zhuwenwen
 * @date 2018/9/8 22:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名和密码进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @Cacheable(cacheNames = "First",key = "#username")
    @Override
    public Integer logon(String username, String password) {
        UserExample example=new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password).andStateEqualTo(1);
        return userMapper.selectByExample(example).size()>0?1:0;
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Override
    public User getUserByUsername(String username) {
        UserExample example=new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        return userMapper.selectByExample(example).stream().findFirst().orElse(null);
    }


}
