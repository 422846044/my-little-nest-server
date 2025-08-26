package top.zhongyingjie.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.admin.vo.server.Server;
import top.zhongyingjie.common.domain.Result;

/**
 * 服务器监控
 *
 */
@RestController
@RequestMapping("/monitor/server")
@Slf4j
public class ServerController {

    @GetMapping()
    public Result<Server> getInfo(){
        Server server = new Server();
        try {
            server.copyTo();
        } catch (Exception e) {
             log.error("系统服务信息获取失败：{}", e.getMessage(), e);
        }
        return Result.success(server);
    }
}
