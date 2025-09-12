package top.zhongyingjie.admin.security.controller;

import top.zhongyingjie.admin.security.constant.SecurityConst;
import top.zhongyingjie.admin.service.JWTTokenService;
import top.zhongyingjie.common.cache.Cache;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.common.enums.ResponseCodeEnum;
import top.zhongyingjie.common.exchandler.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 刷新凭证信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/refresh")
public class RefreshController {

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private Cache<Integer> cache;

    @Value("${jwt.expires}")
    private int expires;

    @Value("${jwt.refresh}")
    private int refresh;

    /**
     * 获取登录刷新凭证
     *
     * @param refreshToken 旧登录刷新凭证
     * @return 统一返回对象
     */
    @GetMapping("/refresh")
    public Result<Map<String, String>> refresh(@RequestParam("refreshToken") String refreshToken) {
        if (!cache.hasKey(SecurityConst.REFRESH_TOKEN_HEADER + "_" + refreshToken)) {
            throw new GlobalException("长时间未登录", ResponseCodeEnum.REFRESH_TOKEN_EXPIRE.getCode());
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
        cache.putAndExp(SecurityConst.TOKEN_HEADER + "_" + newAccessToken,
                1, Integer.toUnsignedLong(expires), TimeUnit.DAYS);
        cache.putAndExp(SecurityConst.REFRESH_TOKEN_HEADER + "_" + newRefreshToken,
                1, Integer.toUnsignedLong(refresh), TimeUnit.DAYS);
        return Result.success(tokenInfo);
    }
}
