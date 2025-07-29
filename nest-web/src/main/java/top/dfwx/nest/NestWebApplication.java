package top.dfwx.nest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "top.dfwx.common",
        "top.dfwx.nest"
})
@MapperScan(basePackages = {
        "top.dfwx.nest.mapper"
})
@EnableScheduling
public class NestWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(NestWebApplication.class, args);
    }
}
