package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.service.QiniuService;
import top.zhongyingjie.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共服务api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private QiniuService qiniuService;

    /**
     * 获取七牛云上传令牌
     *
     * @return 统一返回对象
     */
    @GetMapping("/qiniu/getUploadToken")
    public Result<String> getUploadToken() {
        return Result.success(qiniuService.getUploadToken());
    }
}
