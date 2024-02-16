package fun.dfwh.admin.security.handler;

import cn.hutool.json.JSONUtil;
import fun.dfwh.common.domain.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理未登录请求
 */
@Component
public class TokenAuthenticationErrorHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtil.toJsonStr(new Result(true,401,"未登录！",null)));
    }
}
