package fun.dfwh.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局解决跨域问题
 */
@Configuration
public class CorsConfig {
    /**
     * 配置 corsFilter 自定义过滤器
     * 为了保证 跨域操作 一定会执行，这里有几个点需要注意：
     * 1. 没有在 WebMvcConfigurer 中使用 addCorsMappings 方法完成 cors 的配置，
     *    因为在 addCorsMappings 中配置会使自定义拦截器失效，原因为：
     *      - 当服务端接收到一个请求时，该请求会先经过过滤器，
     *        然后进入拦截器中，然后再进入Mapping映射中的路径所指向的资源，
     *        所以跨域配置在mapping上并不起作用，返回的头信息中并没有配置的跨域信息，浏览器就会报跨域异常。
     *      - 解决方法就是使用 CorsFilter 过滤器配置代替 addCorsMappings 配置，因为过滤器在拦截器前先执行，这样拦截器就不会失效了。
     *
     * 2. 为了保证 CorsFilter 过滤器在过滤器中也一定是最先执行，可以使用 FilterRegistrationBean 设置 CorsFilter 的执行顺序为最高，
     *    这样可以大概率保证 CorsFilter 过滤器会最先执行。
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(false);    // 允许cookies跨域
        corsConfiguration.setMaxAge(1800L);             // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        //corsConfiguration.addAllowedOrigin("*");        // 允许任何“源”(域名)使用
        corsConfiguration.addAllowedOrigin("http://www.dfwx.fun");
        corsConfiguration.addAllowedOriginPattern("http://localhost*");
        corsConfiguration.addAllowedHeader("*");        // 允许任何请求头
        corsConfiguration.addAllowedMethod("*");        // 允许任何方法（get、post等）
        source.registerCorsConfiguration("/**", corsConfiguration);     // 处理所有请求的跨域配置
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(source));   // 注册自定义过滤器
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);  // 优先级最高
        return registrationBean;
    }
}
