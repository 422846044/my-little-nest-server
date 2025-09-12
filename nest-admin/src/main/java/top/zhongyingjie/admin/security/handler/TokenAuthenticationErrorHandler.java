package top.zhongyingjie.admin.security.handler;

import cn.hutool.json.JSONUtil;
import top.zhongyingjie.common.domain.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.zhongyingjie.common.enums.ResponseCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理未登录请求
 *
 * @author Kong
 */
@Component
public class TokenAuthenticationErrorHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtil.toJsonStr(new Result()
                .setCode(ResponseCodeEnum.UNAUTHORIZED.getCode()).setMessage("未登录！")));
    }
}
