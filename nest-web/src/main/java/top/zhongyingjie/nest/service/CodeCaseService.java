package top.zhongyingjie.nest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhongyingjie.nest.code.example.SlidingWindowRateLimiter;

/**
 * 代码案例实现
 *
 * @author Kong
 */
@Service
public class CodeCaseService {

    @Autowired
    private SlidingWindowRateLimiter slidingWindowRateLimiter;

    /**
     * 滑动窗口限流测试
     *
     * @return 请求结果
     */
    public String slidingWindowRateLimiter1() {
        return slidingWindowRateLimiter.allowRequest("limit_key_slidingWindowRateLimiter1") ? "请求成功" : "触发限流";
    }
}
