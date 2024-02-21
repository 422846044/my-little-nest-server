package fun.dfwh.nest.controller;


import cn.hutool.core.util.StrUtil;
import fun.dfwh.common.domain.Result;
import fun.dfwh.nest.domain.FightLogInfo;
import fun.dfwh.nest.service.DjcRequestService;
import fun.dfwh.nest.service.LogService;
import fun.dfwh.nest.service.UserService;
import fun.dfwh.nest.task.DjcTask;
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
            return Result.error().message("请输入QQ号");
        }
        if(!"".equals(cookieNum)) qqNum=cookieNum;
        List<FightLogInfo> fightLogInfoList = logService.getLogInfoByQQNum(Integer.parseInt(qqNum));
        return Result.ok().data(fightLogInfoList);

    }

    @ResponseBody
    @GetMapping("/updateToken")
    public Result updateToken(@RequestParam("token") String token,
                              @RequestParam("qqNum") Integer qqNum){
        boolean result = userService.updateTokenByQQNum(token,qqNum);
        return Result.build(result,"更新");
    }

    @ResponseBody
    @GetMapping("/retryTask")
    public Result retryTask(@RequestParam("qqNum") Integer qqNum){
        djcTask.signTask(qqNum);
        return Result.ok().message("任务已提交");
    }
}
