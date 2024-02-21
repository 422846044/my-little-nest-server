package fun.dfwh.nest.mapper;

import fun.dfwh.nest.entity.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 11th
* @description 针对表【article_info】的数据库操作Mapper
* @createDate 2024-02-17 22:03:59
* @Entity fun.dfwh.nest.entity.ArticleInfo
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    List<ArticleInfo> selectByPage(@Param("lastId") Long lastId,
                                   @Param("pageSize") Integer pageSize,
                                   @Param("isNext") Boolean isNext,
                                   @Param("status") Integer status);

    Integer selectCountByStatus(@Param("status") Integer status);
}
