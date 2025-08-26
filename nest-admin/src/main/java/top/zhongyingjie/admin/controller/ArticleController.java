package top.zhongyingjie.admin.controller;

import top.zhongyingjie.admin.dto.ArticleDTO;
import top.zhongyingjie.admin.dto.ArticlePageQueryDTO;
import top.zhongyingjie.admin.service.ArticleService;
import top.zhongyingjie.admin.vo.ArticleInfoVO;
import top.zhongyingjie.admin.vo.ArticleListVO;
import top.zhongyingjie.admin.vo.HomeDataCountVO;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.common.validator.group.Add;
import top.zhongyingjie.common.validator.group.Draft;
import top.zhongyingjie.common.validator.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 文章信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/article")
@Validated
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加文章
     *
     * @param articleDTO 文章信息数据传输对象
     * @return 统一返回对象
     */
    @PostMapping("/info")
    public Result<Object> addArticle(@RequestBody @Validated(Add.class) ArticleDTO articleDTO){
        articleService.addArticle(articleDTO);
        return Result.success();
    }

    /**
     * 添加草稿
     *
     * @param articleDTO 文章信息数据传输对象
     * @return 统一返回对象
     */
    @PostMapping("/info/draft")
    public Result<Object> addArticleDraft(@RequestBody @Validated(Draft.class) ArticleDTO articleDTO){
        articleService.addArticleDraft(articleDTO);
        return Result.success();
    }

    /**
     * 更新文章信息
     * @param articleDTO 文章信息数据传输对象
     * @return 统一返回对象
     */
    @PutMapping("/info")
    public Result<Object> updateArticle(@RequestBody @Validated(Update.class) ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        return Result.success();
    }

    /**
     * 获取文章统计信息
     *
     * @return 统一返回对象
     */
    @GetMapping("/data/count")
    public Result<HomeDataCountVO> getDataCount(){
        return Result.success(articleService.getDataCount());
    }

    /**
     * 分页获取文章列表信息
     *
     * @param articlePageQuery 文章分页查询参数
     * @return 统一返回对象
     */
    @GetMapping("/list")
    public Result<Result.PageData<ArticleListVO>> getArticleList(@Valid ArticlePageQueryDTO articlePageQuery){
        return articleService.getArticleList(articlePageQuery);
    }

    /**
     * 获取文章详情
     *
     * @param articleId 文章id
     * @return 统一返回对象
     */
    @GetMapping("/info/{articleId}")
    public Result<ArticleInfoVO> getArticleInfo(@PathVariable("articleId") Long articleId){
        return Result.success(articleService.getArticleInfo(articleId));
    }

    /**
     * 删除文章
     *
     * @param articleId 文章id
     * @return 统一返回对象
     */
    @DeleteMapping("/info/{articleId}")
    public Result<Object> delArticleInfo(@PathVariable("articleId") Long articleId){
        articleService.delArticleInfo(articleId);
        return Result.success();
    }
}
