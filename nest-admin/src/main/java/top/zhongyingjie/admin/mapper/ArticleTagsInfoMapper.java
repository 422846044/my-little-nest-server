package top.zhongyingjie.admin.mapper;


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

    ArticleTagsInfo selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(ArticleTagsInfo record);

    int updateByPrimaryKey(ArticleTagsInfo record);

    /**
     * 批量插入
     *
     * @param articleTagsInfos 文章标签信息集合
     * @return 插入条数
     */
    int batchInsert(@Param("articleTagsInfos") List<ArticleTagsInfo> articleTagsInfos);

    /**
     * 通过文章id列表查询标签信息列表
     *
     * @param articleIdList 文章id列表
     * @return 标签信息列表
     */
    List<ArticleTagsInfo> selectByArticleIds(@Param("articleIdList") List<Long> articleIdList);

    /**
     * 通过文章id查询标签代码列表
     *
     * @param articleId 文章id
     * @return 标签代码列表
     */
    List<String> selectTagsCodeByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章id和标签代码批量逻辑删除
     *
     * @param tagsCodes 标签代码列表
     * @param articleId 文章id
     * @return 更新条数
     */
    int batchDeleted(@Param("tagsCodes") List<String> tagsCodes,
                     @Param("articleId") Long articleId);



}
