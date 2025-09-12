package top.zhongyingjie.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.service.CodeCaseService;

/**
 * 代码案例api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/code/case")
public class CodeCaseController {

    @Autowired
    private CodeCaseService codeCaseService;

    /**
     * 滑动窗口限流测试
     *
     * @return 统一返回对象
     */
    @PostMapping("/slidingWindowRateLimiter1")
    public Result<Object> slidingWindowRateLimiter1() {
        return Result.success(codeCaseService.slidingWindowRateLimiter1());
    }

}
