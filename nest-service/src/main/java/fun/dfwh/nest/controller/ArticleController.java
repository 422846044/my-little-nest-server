package fun.dfwh.nest.controller;

import fun.dfwh.common.domain.Result;
import fun.dfwh.nest.service.ArticleService;
import fun.dfwh.nest.vo.ArticleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getArticleByPage")
    public Result getArticleByPage(@RequestParam("lastId") Long lastId,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("isNext") Boolean isNext){
        Map data = articleService.getArticleByPage(lastId,pageSize,isNext);
        return Result.ok().data(data);
    }

    @GetMapping("/getArticleInfoById")
    public Result getArticleInfoById(@RequestParam("id") Long id){
        ArticleInfoVO articleInfoVO = articleService.getArticleInfoById(id);
        return Result.ok().data(articleInfoVO);
    }
}
