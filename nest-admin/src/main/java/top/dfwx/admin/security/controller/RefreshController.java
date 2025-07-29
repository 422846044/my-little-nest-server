package top.dfwx.admin.security.controller;

import top.dfwx.admin.security.constant.SecurityConst;
import top.dfwx.admin.service.JWTTokenService;
import top.dfwx.common.cache.Cache;
import top.dfwx.common.domain.Result;
import top.dfwx.common.exchandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping ("/refresh")
public class RefreshController {

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private Cache<Integer> cache;

    @Value("${jwt.expires}")
    private int expires;
    @Value("${jwt.refresh}")
    private int refresh;

    @GetMapping("/refresh")
    public Result refresh(@RequestParam("refreshToken") String refreshToken){
        if(!cache.hasKey(SecurityConst.REFRESH_TOKEN_HEADER + "_" + refreshToken)){
            throw new GlobalException("refresh invalid",402);
        }
        jwtTokenService.verify(refreshToken);
        Map<String, String> tokenInfo = new HashMap<>();
        // 删除原有token信息
        // 删除原有refreshToken信息
        // 保存到缓存
        String newAccessToken = jwtTokenService.createAccessToken(
                jwtTokenService.getUserId(refreshToken),
                jwtTokenService.getUserName(refreshToken));
        tokenInfo.put("accessToken", newAccessToken);
        String newRefreshToken = jwtTokenService.createRefreshToken(
                jwtTokenService.getUserId(refreshToken),
                jwtTokenService.getUserName(refreshToken));
        tokenInfo.put("refreshToken", newRefreshToken);
        cache.putAndExp(SecurityConst.TOKEN_HEADER + "_" + newAccessToken,1,Integer.toUnsignedLong(expires), TimeUnit.DAYS);
        cache.putAndExp(SecurityConst.REFRESH_TOKEN_HEADER + "_" + newRefreshToken,1,Integer.toUnsignedLong(refresh), TimeUnit.DAYS);
        return Result.ok().data(tokenInfo);
    }
}
