package fun.dfwh.admin.interceptor;

import cn.hutool.core.util.StrUtil;
import fun.dfwh.common.pojo.UserHolder;
import fun.dfwh.common.pojo.UserLoginInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserLoginInfoInterceptor implements HandlerInterceptor {

    //获取用户信息并保存到本地内存
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        if(StrUtil.isNotEmpty(userId)){
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUserId(userId);
            UserHolder.setUserLoginInfo(userLoginInfo);
        }
        return true;
    }

    /**
     * 请求结束前清理本地内存
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUserLoginInfo();
    }
}
