package top.dfwx.admin.mapper;


import top.dfwx.common.entity.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
* @author 11th
* @description 针对表【article_info】的数据库操作Mapper
* @createDate 2024-02-06 11:19:23
* @Entity top.dfwx.admin.entity.ArticleInfo
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    HashMap<String, Object> selectDataCount(@Param("year") int year,
                                            @Param("month") int month,
                                            @Param("day") int day,
                                            @Param("monthThanYear") int monthThanYear,
                                            @Param("monthThanMonth") int monthThanMonth,
                                            @Param("dayThanYear") int dayThanYear,
                                            @Param("dayThanMonth") int dayThanMonth,
                                            @Param("dayThanDay") int dayThanDay,
                                            @Param("draftStatus") int draftStatus);

    List<ArticleInfo> selectByPage(@Param("keyword") String keyword,
                                   @Param("category") Integer category,
                                   @Param("tag") String tag,
                                   @Param("order") String order,
                                   @Param("sort") String sort,
                                   @Param("statusList") List<Integer> statusList);
}
