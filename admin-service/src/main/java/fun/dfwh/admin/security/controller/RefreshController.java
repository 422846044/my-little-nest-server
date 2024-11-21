package fun.dfwh.admin.security.controller;

import fun.dfwh.admin.security.constant.SecurityConst;
import fun.dfwh.admin.service.JWTTokenService;
import fun.dfwh.common.cache.Cache;
import fun.dfwh.common.domain.Result;
import fun.dfwh.common.exchandler.GlobalException;
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
        cache.vagueDel(SecurityConst.TOKEN_HEADER + "_" + "");
        cache.vagueDel(SecurityConst.REFRESH_TOKEN_HEADER + "_" + "");
        String newAccessToken = jwtTokenService.createAccessToken(
                jwtTokenService.getUserId(refreshToken),
                jwtTokenService.getUserName(refreshToken));
        tokenInfo.put("accessToken", newAccessToken);
        String newRefreshToken = jwtTokenService.createRefreshToken(
                jwtTokenService.getUserId(refreshToken),
                jwtTokenService.getUserName(refreshToken));
        tokenInfo.put("refreshToken", newRefreshToken);
        // 保存到缓存
        cache.putAndExp(SecurityConst.TOKEN_HEADER + "_" + newAccessToken,1,Integer.toUnsignedLong(expires), TimeUnit.DAYS);
        cache.putAndExp(SecurityConst.REFRESH_TOKEN_HEADER + "_" + newRefreshToken,1,Integer.toUnsignedLong(refresh), TimeUnit.DAYS);
        return Result.ok().data(tokenInfo);
    }
}
