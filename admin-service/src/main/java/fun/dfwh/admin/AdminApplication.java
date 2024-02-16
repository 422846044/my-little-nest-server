package fun.dfwh.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {
        "fun.dfwh.common",
        "fun.dfwh.admin"
})
@MapperScan(basePackages = {
        "fun.dfwh.admin.mapper"
})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
