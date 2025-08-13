package top.dfwx.nest.controller;


import cn.hutool.core.util.StrUtil;
import top.dfwx.common.domain.Result;
import top.dfwx.nest.domain.FightLogInfo;
import top.dfwx.nest.service.DjcRequestService;
import top.dfwx.nest.service.LogService;
import top.dfwx.nest.service.UserService;
import top.dfwx.nest.task.DjcTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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



    @GetMapping("/index")
    public String index(@RequestParam( name = "qqNum",required = false) String qqNum, HttpServletRequest httpServletRequest, Model model){

        String cookieNum = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("qqNum".equals(name)) cookieNum = cookie.getValue();
            }
        }
        if("".equals(cookieNum)&&StrUtil.isBlank(qqNum)){
            return "query";
        }
        if(!"".equals(cookieNum)) qqNum=cookieNum;
        List<FightLogInfo> fightLogInfoList = logService.getLogInfoByQQNum(Integer.parseInt(qqNum));
        model.addAttribute("logInfoList", fightLogInfoList);
        model.addAttribute("qqNum",qqNum);
        return "index";

    }

    @ResponseBody
    @GetMapping("/query")
    public Result query(@RequestParam( name = "qqNum",required = false) String qqNum, HttpServletRequest httpServletRequest){
        String cookieNum = "";
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                if("qqNum".equals(name)) cookieNum = cookie.getValue();
            }
        }
        if("".equals(cookieNum)&&StrUtil.isBlank(qqNum)){
            return Result.serverError("请输入QQ号");
        }
        if(!"".equals(cookieNum)) qqNum=cookieNum;
        List<FightLogInfo> fightLogInfoList = logService.getLogInfoByQQNum(Integer.parseInt(qqNum));
        return Result.success(fightLogInfoList);

    }

    @ResponseBody
    @GetMapping("/updateToken")
    public Result updateToken(@RequestParam("token") String token,
                              @RequestParam("qqNum") Integer qqNum){
        userService.updateTokenByQQNum(token,qqNum);
        return Result.success();
    }

    @ResponseBody
    @GetMapping("/retryTask")
    public Result retryTask(@RequestParam("qqNum") Integer qqNum){
        djcTask.signTask(qqNum);
        return Result.success("任务已提交");
    }
}
