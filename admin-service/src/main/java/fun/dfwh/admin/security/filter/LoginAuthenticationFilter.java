package fun.dfwh.admin.security.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public LoginAuthenticationFilter(){
        super(new AntPathRequestMatcher("/admin/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(request.getInputStream(), "UTF-8"));
        String buffer;
        StringBuilder jsonStr = new StringBuilder();
        while ((buffer=bufferedReader.readLine())!=null){
            jsonStr.append(buffer);
        }
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        final String username = jsonObject.getStr("username");
        final String password = jsonObject.getStr("password");
        UsernamePasswordAuthenticationToken upAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        return getAuthenticationManager().authenticate(upAuthenticationToken);
    }
}
