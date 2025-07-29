package top.dfwx.admin.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import top.dfwx.admin.security.constant.SecurityConst;
import top.dfwx.admin.security.domain.SecurityUserDetails;
import top.dfwx.admin.service.JWTTokenService;
import top.dfwx.common.cache.Cache;
import top.dfwx.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenService jwtTokenService;
    @Qualifier("securityUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private Cache<Integer> cache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SecurityConst.TOKEN_HEADER);
        //不为空则写入上下文
        if(StrUtil.isNotEmpty(token)){
            // 判断是否存在token信息
            if(!cache.hasKey(SecurityConst.TOKEN_HEADER + "_"+token)){
                //返回 提示使用refresh_token
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JSONUtil.toJsonStr(new Result(true,402,"凭证已过期！",null)));
                return;
            }
            //校验token
            try {
                jwtTokenService.verify(token);
            } catch (TokenExpiredException e) {
                //返回 提示使用refresh_token
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JSONUtil.toJsonStr(new Result(true,402,"凭证已过期！",null)));
                return;
            } catch (Exception e){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(JSONUtil.toJsonStr(new Result(true,401,"凭证校验未通过",null)));
                return;
            }
            String userName = jwtTokenService.getUserName(token);
            //若用户名不为null
            if(StrUtil.isNotEmpty(userName)&& SecurityContextHolder.getContext().getAuthentication()==null){
                SecurityUserDetails userDetails = (SecurityUserDetails) userDetailsService.loadUserByUsername(userName);
                if(jwtTokenService.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
