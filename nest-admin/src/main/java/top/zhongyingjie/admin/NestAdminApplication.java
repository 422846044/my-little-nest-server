package top.zhongyingjie.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 管理系统启动类
 *
 * @author Kong
 */
@SpringBootApplication(scanBasePackages = {
        "top.zhongyingjie.common",
        "top.zhongyingjie.admin"
})
@MapperScan(basePackages = {
        "top.zhongyingjie.admin.mapper"
})
public class NestAdminApplication {

    /**
     * 启动方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(NestAdminApplication.class, args);
    }
}
