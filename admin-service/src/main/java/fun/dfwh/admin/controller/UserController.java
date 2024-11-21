package fun.dfwh.admin.controller;

import fun.dfwh.admin.service.IUserService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/getSimpleUserInfoByUserId")
    public Result getSimpleUserInfoByUserId(@RequestParam Long userId){
        return Result.ok().data(iUserService.getSimpleUserInfoByUserId(userId));
    }
}
