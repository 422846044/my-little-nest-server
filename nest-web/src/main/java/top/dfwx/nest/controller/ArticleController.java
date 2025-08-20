package top.dfwx.nest.controller;

import top.dfwx.common.domain.Result;
import top.dfwx.nest.pojo.dto.ArticlePageQueryDTO;
import top.dfwx.nest.service.ArticleService;
import top.dfwx.nest.vo.ArticleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/getArticleByPage")
    public Result getArticleByPage(@Valid ArticlePageQueryDTO articlePageQuery){
        return Result.success(articleService.getArticleByPage(articlePageQuery));
    }

    @GetMapping("/getArticleInfoById")
    public Result getArticleInfoById(@RequestParam("id") Long id){
        ArticleInfoVO articleInfoVO = articleService.getArticleInfoById(id);
        return Result.success(articleInfoVO);
    }

    @GetMapping("/history")
    public Result getHistory(){
        return Result.success(articleService.getHistory());
    }

    @GetMapping("/count")
    public Result<Long> getCount(){
        return Result.success(articleService.getCount());
    }
}
