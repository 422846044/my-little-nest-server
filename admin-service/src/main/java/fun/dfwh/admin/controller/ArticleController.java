package fun.dfwh.admin.controller;

import fun.dfwh.admin.dto.ArticleDTO;
import fun.dfwh.admin.service.ArticleService;
import fun.dfwh.common.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody ArticleDTO articleDTO){
        articleService.addArticle(articleDTO);
        return Result.ok();
    }
}
