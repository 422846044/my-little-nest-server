package fun.dfwh.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fun.dfwh.admin.security.domain.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTTokenService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expires}")
    private int expires;
    @Value("${jwt.refresh}")
    private int refresh;

    public String createAccessToken(String userId,String userName){
        return JWT.create()
                .withClaim("userId",userId)
                .withClaim("userName",userName)
                .withExpiresAt(DateUtil.offsetDay(new Date(),expires))
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUserId(String token) {
        return JWT.decode(token)
                .getClaim("userId").asString();
    }
    public String getUserName(String token) {
        return JWT.decode(token)
                .getClaim("userName").asString();
    }

    public boolean validateToken(String token, SecurityUserDetails userDetails) {
        boolean result = false;
        try {
            verify(token);
            String userId = getUserId(token);
            String userName = getUserName(token);
            if(StrUtil.equals(userId,userDetails.getUserId())&&
                    StrUtil.equals(userName,userDetails.getUsername())) result = true;
        }finally {
            return result;
        }
    }

    public String createRefreshToken(String userId, String userName) {
        return JWT.create()
                .withClaim("userId",userId)
                .withClaim("userName",userName)
                .withExpiresAt(DateUtil.offsetDay(new Date(),refresh))
                .sign(Algorithm.HMAC256(secret));
    }


    public void verify(String token){
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

}
