package top.zhongyingjie.admin.security.handler;

import cn.hutool.json.JSONUtil;
import top.zhongyingjie.common.domain.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import top.zhongyingjie.common.enums.ResponseCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户认证失败处理器
 *
 * @author Kong
 */
@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String message = exception.getMessage();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtil.toJsonStr(new Result()
                .setCode(ResponseCodeEnum.UNAUTHORIZED.getCode()).setMessage(message)));
    }
}
