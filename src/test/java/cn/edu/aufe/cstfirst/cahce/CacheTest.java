
package cn.edu.aufe.cstfirst.cahce;

import cn.edu.aufe.cstfirst.domian.Blog;
import cn.edu.aufe.cstfirst.service.BlogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author zhuwenwen
 * @date 2018/10/31 14:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {
    @Autowired
    private BlogService blogService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("test1","test2");
        Assert.assertEquals("test2",redisTemplate.opsForValue().get("test1"));
    }

    public void test1(){
        List<Blog> blogs = blogService.listAll("1");
    }
}
