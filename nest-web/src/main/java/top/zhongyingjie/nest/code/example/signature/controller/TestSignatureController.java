package top.zhongyingjie.nest.code.example.signature.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.code.example.signature.dto.SignedRequest;
import top.zhongyingjie.nest.code.example.signature.service.ServerSignatureService;

/**
 * 签名测试api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/test/signature")
public class TestSignatureController {

    /**
     * 测试接口
     *
     * @param signedRequest 签名请求对象
     * @return 统一返回对象
     */
    @PostMapping("/test")
    public Result<Object> testSignature(@RequestBody SignedRequest signedRequest) {
        // 校验签名
        ServerSignatureService serverSignatureService = new ServerSignatureService();
        serverSignatureService.verifyRequest(signedRequest);
        return Result.success();
    }
}
