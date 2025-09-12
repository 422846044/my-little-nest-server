package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.service.SystemService;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统参数信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 获取key对应的value
     *
     * @param key 键
     * @return 统一返回对象
     */
    @GetMapping("/value/{key}")
    public Result<String> getValue(@PathVariable("key") String key) {
        return Result.success(systemService.getValue(key));
    }

}
