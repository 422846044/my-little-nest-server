package fun.dfwh.nest.mapper;

import com.dfwh.djctask.domain.ArticleInfo;

/**
* @author 11th
* @description 针对表【article_info】的数据库操作Mapper
* @createDate 2024-02-04 22:40:39
* @Entity com.dfwh.djctask.domain.ArticleInfo
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

}
