package top.zhongyingjie.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.event.listener.ViewEventListener;

/**
 * 浏览信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private RedisTemplate<String, Long> longRedisTemplate;

    /**
     * 获取浏览量统计
     *
     * @return 统一返回对象
     */
    @GetMapping("/count")
    public Result<Long> getCount() {
        return Result.success(longRedisTemplate.opsForValue().get(ViewEventListener.ALL_VIEW_COUNT_KEY));
    }
}
