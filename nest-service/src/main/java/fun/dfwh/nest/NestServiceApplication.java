package fun.dfwh.nest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {
        "fun.dfwh.common",
        "fun.dfwh.nest"
})
@MapperScan(basePackages = {
        "fun.dfwh.nest.mapper"
})
@EnableScheduling
public class NestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NestServiceApplication.class);
    }
}
