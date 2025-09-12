package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.service.IUserService;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用户信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 获取用户简单信息
     *
     * @param uid 用户id
     * @return 统一返回对象
     */
    @GetMapping("/getSimpleUserInfoByUserId")
    public Result<Map<String, String>> getSimpleUserInfoByUserId(@RequestParam("userId") Long uid) {
        return Result.success(iUserService.getSimpleUserInfoByUserId(uid));
    }
}
