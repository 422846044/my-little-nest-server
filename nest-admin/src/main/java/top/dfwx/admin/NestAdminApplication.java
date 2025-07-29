package top.dfwx.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "top.dfwx.common",
        "top.dfwx.admin"
})
@MapperScan(basePackages = {
        "top.dfwx.admin.mapper"
})
public class NestAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(NestAdminApplication.class, args);
    }
}
