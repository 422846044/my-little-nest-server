package top.zhongyingjie.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "top.zhongyingjie.common",
        "top.zhongyingjie.admin"
})
@MapperScan(basePackages = {
        "top.zhongyingjie.admin.mapper"
})
public class NestAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(NestAdminApplication.class, args);
    }
}
