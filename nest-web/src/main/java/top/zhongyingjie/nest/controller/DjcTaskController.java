package top.zhongyingjie.nest.controller;


import cn.hutool.core.util.StrUtil;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.domain.FightLogInfo;
import top.zhongyingjie.nest.service.DjcRequestService;
import top.zhongyingjie.nest.service.LogService;
import top.zhongyingjie.nest.service.UserService;
import top.zhongyingjie.nest.task.DjcTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 道具城任务api
 *
 * @author Kong
 */
@Controller
@RequestMapping("/djc/task")
public class DjcTaskController {

    @Autowired
    private DjcRequestService djcRequestService;
    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @Autowired
    private DjcTask djcTask;

    /**
     * 路由
     *
     * @param qqNum              QQ号
     * @param httpServletRequest 请求对象
     * @param model              模型
     * @return 跳转路径
     */
    @GetMapping("/index")
    public String index(@RequestParam(name = "qqNum",
            required = false) String qqNum, HttpServletRequest httpServletRequest, Model model) {
        String cookieNum = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("qqNum".equals(name)) {
                    cookieNum = cookie.getValue();
                }
            }
        }
        if ("".equals(cookieNum) && StrUtil.isBlank(qqNum)) {
            return "query";
        }
        if (!"".equals(cookieNum)) {
            qqNum = cookieNum;
        }
        List<FightLogInfo> fightLogInfoList = logService.getLogInfoByQQNum(Integer.parseInt(qqNum));
        model.addAttribute("logInfoList", fightLogInfoList);
        model.addAttribute("qqNum", qqNum);
        return "index";
    }

    /**
     * 查询日志信息
     *
     * @param qqNum              QQ号
     * @param httpServletRequest 请求对象
     * @return 统一返回对象
     */
    @ResponseBody
    @GetMapping("/query")
    public Result<List<FightLogInfo>> query(
            @RequestParam(name = "qqNum", required = false) String qqNum, HttpServletRequest httpServletRequest) {
        String cookieNum = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if ("qqNum".equals(name)) {
                    cookieNum = cookie.getValue();
                }
            }
        }
        if ("".equals(cookieNum) && StrUtil.isBlank(qqNum)) {
            return Result.serverError("请输入QQ号");
        }
        if (!"".equals(cookieNum)) {
            qqNum = cookieNum;
        }
        return Result.success(logService.getLogInfoByQQNum(Integer.parseInt(qqNum)));

    }

    /**
     * 更新凭证
     *
     * @param token 令牌
     * @param qqNum QQ号
     * @return 统一返回对象
     */
    @ResponseBody
    @GetMapping("/updateToken")
    public Result<Object> updateToken(@RequestParam("token") String token,
                                      @RequestParam("qqNum") Integer qqNum) {
        userService.updateTokenByQQNum(token, qqNum);
        return Result.success();
    }

    /**
     * 重试任务
     *
     * @param qqNum QQ号
     * @return 统一返回对象
     */
    @ResponseBody
    @GetMapping("/retryTask")
    public Result<String> retryTask(@RequestParam("qqNum") Integer qqNum) {
        djcTask.signTask(qqNum);
        return Result.success("任务已提交");
    }
}
