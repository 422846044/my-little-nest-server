package fun.dfwh.nest.controller;

import fun.dfwh.common.domain.Result;
import fun.dfwh.nest.pojo.dto.ArticlePageQueryDTO;
import fun.dfwh.nest.service.ArticleService;
import fun.dfwh.nest.vo.ArticleInfoVO;
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
        return Result.ok().data(articleService.getArticleByPage(articlePageQuery));
    }

    @GetMapping("/getArticleInfoById")
    public Result getArticleInfoById(@RequestParam("id") Long id){
        ArticleInfoVO articleInfoVO = articleService.getArticleInfoById(id);
        return Result.ok().data(articleInfoVO);
    }
}
