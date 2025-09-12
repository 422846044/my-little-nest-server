package top.zhongyingjie.admin.security.handler;

import cn.hutool.json.JSONUtil;
import top.zhongyingjie.common.domain.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.zhongyingjie.common.enums.ResponseCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理权限不足的请求
 *
 * @author Kong
 */
@Component
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtil.toJsonStr(new Result()
                .setCode(ResponseCodeEnum.FORBIDDEN.getCode()).setMessage("暂无权限")));
    }
}
