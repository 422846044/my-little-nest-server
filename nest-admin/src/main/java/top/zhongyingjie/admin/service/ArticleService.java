package top.zhongyingjie.admin.service;


import top.zhongyingjie.admin.dto.ArticleDTO;
import top.zhongyingjie.admin.dto.ArticlePageQueryDTO;
import top.zhongyingjie.admin.vo.ArticleInfoVO;
import top.zhongyingjie.admin.vo.ArticleListVO;
import top.zhongyingjie.admin.vo.HomeDataCountVO;
import top.zhongyingjie.common.domain.Result;

public interface ArticleService {

    void addArticle(ArticleDTO articleDTO);

    HomeDataCountVO getDataCount();

    Result<Result.PageData<ArticleListVO>> getArticleList(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfo(Long articleId);

    void updateArticle(ArticleDTO articleDTO);

    void addArticleDraft(ArticleDTO articleDTO);

    void delArticleInfo(Long articleId);
}
