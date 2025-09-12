package top.zhongyingjie.admin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 加密配置类
 *
 * @author Kong
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * 获取加密器
     *
     * @return 加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
