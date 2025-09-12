package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.common.entity.ArticleTagsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Kong
*/
public interface ArticleTagsInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagsInfo record);

    int insertSelective(ArticleTagsInfo record);

    ArticleTagsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagsInfo record);

    int updateByPrimaryKey(ArticleTagsInfo record);

    /**
     * 通过文章id查询标签代码列表
     *
     * @param articleId 文章id
     * @return 标签代码列表
     */
    List<Integer> selectTagsCodeByArticleId(Long articleId);

    /**
     * 根据文章id列表查询文章标签信息列表
     *
     * @param articleIdList 文章id列表
     * @return 文章标签信息列表
     */
    List<ArticleTagsInfo> selectByArticleIds(@Param("articleIdList") List<Long> articleIdList);
}
