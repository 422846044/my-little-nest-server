package top.zhongyingjie.nest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "top.zhongyingjie.common",
        "top.zhongyingjie.nest"
})
@MapperScan(basePackages = {
        "top.zhongyingjie.nest.mapper"
})
@EnableScheduling
public class NestWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NestWebApplication.class, args);
    }
}
