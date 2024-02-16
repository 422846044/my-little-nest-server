package fun.dfwh.admin.security.handler;

import cn.hutool.json.JSONUtil;
import fun.dfwh.admin.security.domain.SecurityUserDetails;
import fun.dfwh.admin.service.JWTTokenService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 密码校验通过处理
 */
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTTokenService jwtTokenService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成token
        String accessToken = jwtTokenService
                .createAccessToken(userDetails.getUserId(), userDetails.getUsername());
        String refreshToken = jwtTokenService
                .createRefreshToken(userDetails.getUserId(),userDetails.getUsername());
        //返回
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        HashMap<String,String> tokenData = new HashMap<>();
        tokenData.put("accessToken",accessToken);
        tokenData.put("refreshToken",refreshToken);
        response.getWriter().write(JSONUtil.toJsonStr(new Result(true,200,"登录成功",tokenData)));
    }
}
