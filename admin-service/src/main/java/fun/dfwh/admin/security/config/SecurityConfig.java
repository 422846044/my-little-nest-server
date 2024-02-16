package fun.dfwh.admin.security.config;

import fun.dfwh.admin.security.filter.TokenAuthenticationFilter;
import fun.dfwh.admin.security.handler.RequestAccessDeniedHandler;
import fun.dfwh.admin.security.handler.TokenAuthenticationErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                .antMatchers("/admin/login")
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

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter();
    }
}
