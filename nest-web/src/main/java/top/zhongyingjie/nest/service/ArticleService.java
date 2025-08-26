package top.zhongyingjie.nest.service;

import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.pojo.dto.ArticlePageQueryDTO;
import top.zhongyingjie.nest.vo.ArticleInfoVO;
import top.zhongyingjie.nest.vo.ArticleListVO;
import top.zhongyingjie.nest.vo.HistoryListVO;

import java.util.List;

public interface ArticleService {
    Result<Result.PageData<ArticleListVO>> getArticleByPage(ArticlePageQueryDTO articlePageQuery);

    ArticleInfoVO getArticleInfoById(Long id);

    List<HistoryListVO> getHistory();

    Long getCount();
}
