package top.dfwx.nest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dfwx.common.domain.Result;
import top.dfwx.nest.service.UpdatesService;
import top.dfwx.nest.vo.UpdatesVO;

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
