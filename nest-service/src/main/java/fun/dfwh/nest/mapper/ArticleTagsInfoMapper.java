package fun.dfwh.nest.mapper;

import fun.dfwh.common.entity.ArticleTagsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 11th
* @description 针对表【article_tags_info】的数据库操作Mapper
* @createDate 2024-02-19 10:42:14
* @Entity fun.dfwh.common.entity.ArticleTagsInfo
*/
public interface ArticleTagsInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleTagsInfo record);

    int insertSelective(ArticleTagsInfo record);

    ArticleTagsInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleTagsInfo record);

    int updateByPrimaryKey(ArticleTagsInfo record);

    List<Integer> selectTagsCodeByArticleId(Long articleId);

    List<ArticleTagsInfo> selectByArticleIds(@Param("articleIdList") List<Long> articleIdList);
}
