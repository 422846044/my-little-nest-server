package top.zhongyingjie.admin.service;


import top.zhongyingjie.admin.dto.ArticleDTO;
import top.zhongyingjie.admin.dto.ArticlePageQueryDTO;
import top.zhongyingjie.admin.vo.ArticleInfoVO;
import top.zhongyingjie.admin.vo.ArticleListVO;
import top.zhongyingjie.admin.vo.HomeDataCountVO;
import top.zhongyingjie.common.domain.Result;

/**
 * 文章服务接口
 *
 * @author Kong
 */
public interface ArticleService {

    /**
     * 添加文章
     *
     * @param articleDTO 文章信息数据传输对象
     */
    void addArticle(ArticleDTO articleDTO);

    /**
     * 获取数据统计信息视图对象
     *
     * @return 数据统计信息视图对象
     */
    HomeDataCountVO getDataCount();

    /**
     * 分页查询文章列表
     *
     * @param articlePageQuery 文章查询参数
     * @return 文章分类列表信息
     */
    Result<Result.PageData<ArticleListVO>> getArticleList(ArticlePageQueryDTO articlePageQuery);

    /**
     * 根据文章id获取文章信息
     *
     * @param articleId 文章id
     * @return 文章信息视图对象
     */
    ArticleInfoVO getArticleInfo(Long articleId);

    /**
     * 根据文章id更新文章信息
     *
     * @param articleDTO 文章信息数据传输对象
     */
    void updateArticle(ArticleDTO articleDTO);

    /**
     * 添加文章草稿，若存在则更新
     *
     * @param articleDTO 文章信息数据传输对象
     */
    void addArticleDraft(ArticleDTO articleDTO);

    /**
     * 逻辑删除文章信息
     *
     * @param articleId 文章id
     */
    void delArticleInfo(Long articleId);
}
