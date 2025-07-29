package top.dfwx.admin.service;


import com.github.pagehelper.PageInfo;
import top.dfwx.admin.dto.ArticleDTO;
import top.dfwx.admin.dto.ArticlePageQueryDTO;
import top.dfwx.admin.vo.ArticleInfoVO;
import top.dfwx.admin.vo.HomeDataCountVO;

public interface ArticleService {

    void addArticle(ArticleDTO articleDTO);

    HomeDataCountVO getDataCount();

    PageInfo getArticleList(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfo(Long articleId);

    void updateArticle(ArticleDTO articleDTO);

    void addArticleDraft(ArticleDTO articleDTO);
}
