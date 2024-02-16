package fun.dfwh.admin.security.controller;

import cn.hutool.core.util.StrUtil;
import fun.dfwh.admin.security.constant.SecurityConst;
import fun.dfwh.admin.service.JWTTokenService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class RefreshController {

    @Autowired
    private JWTTokenService jwtTokenService;

    @GetMapping("/refresh")
    public Result refresh(HttpHeaders httpHeaders){
        String refreshToken = httpHeaders.getFirst(SecurityConst.REFRESH_TOKEN_HEADER);
        jwtTokenService.verify(refreshToken);
        return Result.ok().data(jwtTokenService.createRefreshToken(
                        jwtTokenService.getUserId(refreshToken),
                        jwtTokenService.getUserName(refreshToken)));
    }
}
