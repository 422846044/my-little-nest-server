package fun.dfwh.admin.controller;

import fun.dfwh.admin.service.UserService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getSimpleUserInfoByUserId")
    private Result getSimpleUserInfoByUserId(@RequestParam Long userId){
        Map data = userService.getSimpleUserInfoByUserId(userId);
        return Result.ok().data(data);
    }
}
