package top.zhongyingjie.nest.controller;

import org.springframework.web.bind.annotation.*;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.pojo.dto.ArticlePageQueryDTO;
import top.zhongyingjie.nest.service.ArticleService;
import top.zhongyingjie.nest.vo.ArticleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import top.zhongyingjie.nest.vo.ArticleListVO;
import top.zhongyingjie.nest.vo.HistoryListVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 文章信息api
 *
 * @author Kong
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章列表分页查询
     *
     * @param articlePageQuery 文章列表分页查询参数
     * @return 统一返回对象
     */
    @GetMapping("/list")
    public Result<Result.PageData<ArticleListVO>> listArticleByPage(@Valid ArticlePageQueryDTO articlePageQuery) {
        return articleService.getArticleByPage(articlePageQuery);
    }

    /**
     * 获取文章信息详情
     *
     * @param id 文章id
     * @return 统一返回对象
     */
    @GetMapping("/info/{id}")
    public Result<ArticleInfoVO> getArticleInfoById(@PathVariable("id") Long id) {
        return Result.success(articleService.getArticleInfoById(id));
    }

    /**
     * 获取文章归档信息列表
     *
     * @return 统一返回对象
     */
    @GetMapping("/history")
    public Result<List<HistoryListVO>> getHistory() {
        return Result.success(articleService.getHistory());
    }

    /**
     * 获取文章数量统计
     *
     * @return 统一返回对象
     */
    @GetMapping("/count")
    public Result<Long> getCount() {
        return Result.success(articleService.getCount());
    }
}
