package fun.dfwh.nest.mapper;

import fun.dfwh.common.entity.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 11th
* @description 针对表【article_info】的数据库操作Mapper
* @createDate 2024-02-17 22:03:59
* @Entity fun.dfwh.common.entity.ArticleInfo
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    List<ArticleInfo> selectByPage(@Param("keyword") String keyword,
                                   @Param("category") Integer category,
                                   @Param("tag") String tag,
                                   @Param("order") String order,
                                   @Param("sort") String sort,
                                   @Param("status") Integer status);

    Integer selectCountByStatus(@Param("status") Integer status);
}
