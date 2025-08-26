package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.service.IUserService;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/getSimpleUserInfoByUserId")
    public Result getSimpleUserInfoByUserId(@RequestParam Long userId){
        return Result.success(iUserService.getSimpleUserInfoByUserId(userId));
    }
}
