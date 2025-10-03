package top.zhongyingjie.nest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * web启动类
 *
 * @author Kong
 */
@SpringBootApplication(scanBasePackages = {
        "top.zhongyingjie.common.*",
        "top.zhongyingjie.nest.*"
})
@MapperScan(basePackages = {
        "top.zhongyingjie.nest.mapper"
})
@EnableScheduling
@EnableAsync
public class NestWebApplication {

    /**
     * 启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(NestWebApplication.class, args);
    }
}
