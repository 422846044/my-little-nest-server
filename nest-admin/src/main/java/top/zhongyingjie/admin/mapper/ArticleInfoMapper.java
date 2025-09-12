package top.zhongyingjie.admin.mapper;


import top.zhongyingjie.common.annotation.IgnoreParameterNumber;
import top.zhongyingjie.common.entity.ArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
* @author Kong
*/
public interface ArticleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);

    ArticleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    /**
     * 获取文章数量统计信息
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @param monthThanYear 被比较年份
     * @param monthThanMonth 被比较月份
     * @param dayThanYear 被比较年
     * @param dayThanMonth 被比较月
     * @param dayThanDay 被比较天
     * @param draftStatus 草稿状态
     * @return 文章数量统计信息
     */
    @IgnoreParameterNumber
    HashMap<String, Object> selectDataCount(@Param("year") int year,
                                            @Param("month") int month,
                                            @Param("day") int day,
                                            @Param("monthThanYear") int monthThanYear,
                                            @Param("monthThanMonth") int monthThanMonth,
                                            @Param("dayThanYear") int dayThanYear,
                                            @Param("dayThanMonth") int dayThanMonth,
                                            @Param("dayThanDay") int dayThanDay,
                                            @Param("draftStatus") int draftStatus);

    /**
     * 根据条件分页查询
     *
     * @param keyword 关键字
     * @param category 分类代码
     * @param tag 标签
     * @param order 排序字段
     * @param sort 排序规则
     * @param statusList 状态集合
     * @return 文章列表
     */
    @IgnoreParameterNumber
    List<ArticleInfo> selectByPage(@Param("keyword") String keyword,
                                   @Param("category") Integer category,
                                   @Param("tag") String tag,
                                   @Param("order") String order,
                                   @Param("sort") String sort,
                                   @Param("statusList") List<Integer> statusList);
}
