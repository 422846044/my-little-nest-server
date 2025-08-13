package top.dfwx.admin.security.handler;

import cn.hutool.json.JSONUtil;
import top.dfwx.admin.security.constant.SecurityConst;
import top.dfwx.admin.security.domain.SecurityUserDetails;
import top.dfwx.admin.service.JWTTokenService;
import top.dfwx.common.cache.Cache;
import top.dfwx.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.concurrent.TimeUnit;

/**
 * 密码校验通过处理
 */
@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JWTTokenService jwtTokenService;
    @Autowired
    private Cache<Integer> cache;
    @Value("${jwt.expires}")
    private int expires;
    @Value("${jwt.refresh}")
    private int refresh;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成token
        String accessToken = jwtTokenService
                .createAccessToken(userDetails.getUserId(), userDetails.getUsername());
        String refreshToken = jwtTokenService
                .createRefreshToken(userDetails.getUserId(),userDetails.getUsername());
        cache.putAndExp(SecurityConst.TOKEN_HEADER + "_" + accessToken,1,Integer.toUnsignedLong(expires), TimeUnit.DAYS);
        cache.putAndExp(SecurityConst.REFRESH_TOKEN_HEADER + "_" + refreshToken,1,Integer.toUnsignedLong(refresh), TimeUnit.DAYS);
        //返回
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        HashMap<String,String> tokenData = new HashMap<>();
        tokenData.put("accessToken",accessToken);
        tokenData.put("refreshToken",refreshToken);
        response.getWriter().write(JSONUtil.toJsonStr(Result.success(tokenData).setMessage("登录成功")));
    }
}
