package fun.dfwh.admin.controller;

import fun.dfwh.admin.dto.ArticleDTO;
import fun.dfwh.admin.dto.ArticlePageQueryDTO;
import fun.dfwh.admin.service.ArticleService;
import fun.dfwh.common.domain.Result;
import fun.dfwh.common.validator.group.Add;
import fun.dfwh.common.validator.group.Draft;
import fun.dfwh.common.validator.group.Update;
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
        return Result.ok();
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
        return Result.ok();
    }

    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody @Validated(Update.class) ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        return Result.ok();
    }

    @GetMapping("/dataCount")
    public Result getDataCount(){
        return Result.ok().data(articleService.getDataCount());
    }

    @GetMapping("/list")
    public Result getArticleList(@Valid ArticlePageQueryDTO articlePageQuery){
        return Result.ok().data(articleService.getArticleList(articlePageQuery));
    }

    @GetMapping("/info/{articleId}")
    public Result getArticleInfo(@PathVariable("articleId") Long articleId){
        return Result.ok().data(articleService.getArticleInfo(articleId));
    }

}
