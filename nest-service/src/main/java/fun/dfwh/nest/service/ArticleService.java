package fun.dfwh.nest.service;

import fun.dfwh.nest.vo.ArticleInfoVO;

import java.util.Map;

public interface ArticleService {
    Map getArticleByPage(Long lastId, Integer pageSize, Boolean isNext);

    ArticleInfoVO getArticleInfoById(Long id);
}
