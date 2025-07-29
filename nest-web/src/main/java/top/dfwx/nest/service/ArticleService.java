package top.dfwx.nest.service;

import com.github.pagehelper.PageInfo;
import top.dfwx.nest.pojo.dto.ArticlePageQueryDTO;
import top.dfwx.nest.vo.ArticleInfoVO;
import top.dfwx.nest.vo.HistoryListVO;

import java.util.List;

public interface ArticleService {
    PageInfo getArticleByPage(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfoById(Long id);

    List<HistoryListVO> getHistory();

}
