package fun.dfwh.admin.security.config;

import fun.dfwh.admin.security.filter.LoginAuthenticationFilter;
import fun.dfwh.admin.security.handler.UserAuthenticationFailureHandler;
import fun.dfwh.admin.security.handler.UserAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 配置登录过滤器
 */
@Configuration
public class LoginAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private UserAuthenticationSuccessHandler successHandler;
    @Autowired
    private UserAuthenticationFailureHandler failureHandler;
    @Qualifier("securityUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void configure(HttpSecurity builder) throws Exception {
        LoginAuthenticationFilter loginFilter = new LoginAuthenticationFilter();
        loginFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
        loginFilter.setAuthenticationSuccessHandler(successHandler);
        loginFilter.setAuthenticationFailureHandler(failureHandler);
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        builder.authenticationProvider(provider);
        builder.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
