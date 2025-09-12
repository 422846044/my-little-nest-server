package top.zhongyingjie.nest.service;

import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.nest.pojo.dto.ArticlePageQueryDTO;
import top.zhongyingjie.nest.vo.ArticleInfoVO;
import top.zhongyingjie.nest.vo.ArticleListVO;
import top.zhongyingjie.nest.vo.HistoryListVO;

import java.util.List;

/**
 * 文章服务接口
 *
 * @author Kong
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     *
     * @param articlePageQuery 文章查询参数
     * @return 文章列表分页信息
     */
    Result<Result.PageData<ArticleListVO>> getArticleByPage(ArticlePageQueryDTO articlePageQuery);

    /**
     * 根据文章id获取文章信息
     *
     * @param id 文章id
     * @return 文章信息视图对象
     */
    ArticleInfoVO getArticleInfoById(Long id);

    /**
     * 获取归档信息
     *
     * @return 归档信息视图对象
     */
    List<HistoryListVO> getHistory();

    /**
     * 获取文章数量统计
     *
     * @return 文章数量
     */
    Long getCount();
}
