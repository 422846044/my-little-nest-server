package top.zhongyingjie.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ServerController {

    private static final Logger log = LoggerFactory.getLogger(ServerController.class);

    /**
     * 获取服务器信息
     *
     * @return 统一返回对象
     */
    @GetMapping()
    public Result<Server> getInfo() {
        Server server = new Server();
        try {
            server.copyTo();
        } catch (Exception e) {
            log.error("系统服务信息获取失败：{}", e.getMessage(), e);
        }
        return Result.success(server);
    }
}
