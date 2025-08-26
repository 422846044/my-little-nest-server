package top.zhongyingjie.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.service.UserInfoService;

import java.util.Map;

/**
 * @author atulan_zyj
 * @date 2025/8/14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/simpleInfo/{uid}")
    public Result<Map<String, Object>> getSimpleInfo(@PathVariable("uid") Long uid){
        return Result.success(userInfoService.getSimpleInfo(uid));
    }
}
