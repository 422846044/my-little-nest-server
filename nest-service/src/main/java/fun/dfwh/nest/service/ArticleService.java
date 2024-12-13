package fun.dfwh.nest.service;

import com.github.pagehelper.PageInfo;
import fun.dfwh.nest.pojo.dto.ArticlePageQueryDTO;
import fun.dfwh.nest.vo.ArticleInfoVO;
import fun.dfwh.nest.vo.HistoryListVO;

import java.util.List;

public interface ArticleService {
    PageInfo getArticleByPage(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfoById(Long id);

    List<HistoryListVO> getHistory();

}
