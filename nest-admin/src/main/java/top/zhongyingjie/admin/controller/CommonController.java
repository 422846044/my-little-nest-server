package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.service.QiniuService;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private QiniuService qiniuService;

    @GetMapping("/qiniu/getUploadToken")
    public Result getUploadToken(){
        return Result.success(qiniuService.getUploadToken());
    }
}
