package top.zhongyingjie.admin.security.config;

import top.zhongyingjie.admin.security.filter.TokenAuthenticationFilter;
import top.zhongyingjie.admin.security.handler.RequestAccessDeniedHandler;
import top.zhongyingjie.admin.security.handler.TokenAuthenticationErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类
 *
 * @author Kong
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginAuthenticationSecurityConfig config;
    @Autowired
    private TokenAuthenticationErrorHandler tokenAuthenticationErrorHandler;
    @Autowired
    private RequestAccessDeniedHandler requestAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //禁用表单登录
                .disable()
                .apply(config)
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/refresh/refresh")
                .permitAll()
                .antMatchers("/role").hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(tokenAuthenticationErrorHandler)
                .accessDeniedHandler(requestAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();

    }

    /**
     * 获取令牌认证过滤器
     *
     * @return 令牌认证过滤器
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
