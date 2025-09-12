package top.zhongyingjie.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zhongyingjie.admin.security.domain.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * jwt令牌服务
 *
 * @author Kong
 */
@Service
public class JWTTokenService {

    private static final Logger log = LoggerFactory.getLogger(JWTTokenService.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires}")
    private int expires;

    @Value("${jwt.refresh}")
    private int refresh;

    /**
     * 创建访问令牌
     *
     * @param userId   用户id
     * @param userName 用户名称
     * @return token
     */
    public String createAccessToken(String userId, String userName) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withExpiresAt(DateUtil.offsetDay(new Date(), expires))
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 从token中解析用户id
     *
     * @param token token
     * @return 用户id
     */
    public String getUserId(String token) {
        return JWT.decode(token)
                .getClaim("userId").asString();
    }

    /**
     * 从token中解析用户名称
     *
     * @param token token
     * @return 用户名称
     */
    public String getUserName(String token) {
        return JWT.decode(token)
                .getClaim("userName").asString();
    }

    /**
     * 校验token正确性
     *
     * @param token       token
     * @param userDetails 用户详情
     * @return 是否正确
     */
    public boolean validateToken(String token, SecurityUserDetails userDetails) {
        boolean result = false;
        try {
            verify(token);
            String userId = getUserId(token);
            String userName = getUserName(token);
            if (StrUtil.equals(userId, userDetails.getUserId())
                    && StrUtil.equals(userName, userDetails.getUsername())) {
                result = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    /**
     * 生成刷新令牌
     *
     * @param userId   用户id
     * @param userName 用户名称
     * @return refreshToken
     */
    public String createRefreshToken(String userId, String userName) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("userName", userName)
                .withExpiresAt(DateUtil.offsetDay(new Date(), refresh))
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 校验token
     *
     * @param token token
     */
    public void verify(String token) {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

}
