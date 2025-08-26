package top.zhongyingjie.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.service.UpdatesService;
import top.zhongyingjie.nest.vo.UpdatesVO;

import java.util.List;

@RestController
@RequestMapping("/updates")
public class UpdatesController {

    @Autowired
    private UpdatesService updatesService;

    @GetMapping("/new")
    public Result<List<UpdatesVO>> getNewUpdates(){
        return Result.success(updatesService.getNewUpdates());
    }
}
