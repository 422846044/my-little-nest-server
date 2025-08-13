package top.dfwx.admin.controller;

import top.dfwx.admin.dto.ArticleDTO;
import top.dfwx.admin.dto.ArticlePageQueryDTO;
import top.dfwx.admin.service.ArticleService;
import top.dfwx.common.domain.Result;
import top.dfwx.common.validator.group.Add;
import top.dfwx.common.validator.group.Draft;
import top.dfwx.common.validator.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody @Validated(Add.class) ArticleDTO articleDTO){
        articleService.addArticle(articleDTO);
        return Result.success();
    }

    /**
     * 添加草稿
     *
     * @param articleDTO
     * @return
     */
    @PostMapping("/addArticleDraft")
    public Result addArticleDraft(@RequestBody @Validated(Draft.class) ArticleDTO articleDTO){
        articleService.addArticleDraft(articleDTO);
        return Result.success();
    }

    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody @Validated(Update.class) ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        return Result.success();
    }

    @GetMapping("/dataCount")
    public Result getDataCount(){
        return Result.success(articleService.getDataCount());
    }

    @GetMapping("/list")
    public Result getArticleList(@Valid ArticlePageQueryDTO articlePageQuery){
        return Result.success(articleService.getArticleList(articlePageQuery));
    }

    @GetMapping("/info/{articleId}")
    public Result getArticleInfo(@PathVariable("articleId") Long articleId){
        return Result.success(articleService.getArticleInfo(articleId));
    }

}
