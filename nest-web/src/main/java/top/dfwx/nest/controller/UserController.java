package top.dfwx.nest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dfwx.common.domain.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author atulan_zyj
 * @date 2025/8/14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/simpleInfo/{uid}")
    public Result getSimpleInfo(@PathVariable("uid") Long uid){
        Map<String, String> data = new HashMap<>();
        String nickName = "Kong",avatar = "http://cdn.zhongyingjie.top/3444e70e03a2-4236-832e-2d8970e9a3f0.jpg";
        data.put("nickName", nickName);
        data.put("avatar", avatar);
        return Result.success(data);
    }
}
