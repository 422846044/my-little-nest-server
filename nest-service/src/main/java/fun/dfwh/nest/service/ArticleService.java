package fun.dfwh.nest.service;

import com.github.pagehelper.PageInfo;
import fun.dfwh.nest.pojo.dto.ArticlePageQueryDTO;
import fun.dfwh.nest.vo.ArticleInfoVO;

import java.util.Map;

public interface ArticleService {
    PageInfo getArticleByPage(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfoById(Long id);
}
