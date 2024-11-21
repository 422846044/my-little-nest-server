package fun.dfwh.admin.mapper;


import fun.dfwh.common.entity.ArticleTagsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【article_tags_info】的数据库操作Mapper
* @createDate 2024-11-20 16:43:15
* @Entity fun.dfwh.admin.entity.ArticleTagsInfo
*/
public interface ArticleTagsInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagsInfo record);

    int insertSelective(ArticleTagsInfo record);

    ArticleTagsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagsInfo record);

    int updateByPrimaryKey(ArticleTagsInfo record);

    int batchInsert(@Param("articleTagsInfos") List<ArticleTagsInfo> articleTagsInfos);

    List<ArticleTagsInfo> selectByArticleIds(@Param("articleIdList") List<Long> articleIdList);

    List<String> selectTagsCodeByArticleId(@Param("articleId") Long articleId);

    int batchDeleted(@Param("tagsCodes") List<String> tagsCodes,
                     @Param("articleId") Long articleId);



}
