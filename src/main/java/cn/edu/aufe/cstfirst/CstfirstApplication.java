package cn.edu.aufe.cstfirst;

import cn.edu.aufe.cstfirst.common.annotation.EnableAuthentication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = "cn.edu.aufe.cstfirst.mapper")
@EnableAuthentication
@EnableTransactionManagement
public class CstfirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(CstfirstApplication.class, args);
    }
}
