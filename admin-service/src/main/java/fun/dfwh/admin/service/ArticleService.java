package fun.dfwh.admin.service;


import com.github.pagehelper.PageInfo;
import fun.dfwh.admin.dto.ArticleDTO;
import fun.dfwh.admin.dto.ArticlePageQueryDTO;
import fun.dfwh.admin.vo.ArticleInfoVO;
import fun.dfwh.admin.vo.HomeDataCountVO;

public interface ArticleService {

    void addArticle(ArticleDTO articleDTO);

    HomeDataCountVO getDataCount();

    PageInfo getArticleList(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfo(Long articleId);

    void updateArticle(ArticleDTO articleDTO);
}
