package fun.dfwh.admin.mapper;


import fun.dfwh.admin.entity.ArticleInfo;

/**
* @author 11th
* @description 针对表【article_info】的数据库操作Mapper
* @createDate 2024-02-06 11:19:23
* @Entity fun.dfwh.admin.entity.ArticleInfo
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

}
