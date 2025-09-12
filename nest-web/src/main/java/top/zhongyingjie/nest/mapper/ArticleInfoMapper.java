package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.common.annotation.IgnoreParameterNumber;
import top.zhongyingjie.common.entity.ArticleInfo;
import top.zhongyingjie.nest.vo.HistoryListVO;
import org.apache.ibatis.annotations.Param;

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
     * 根据条件分页查询
     *
     * @param keyword 关键字
     * @param category 分类代码
     * @param tag 标签代码
     * @param year 年
     * @param month 月
     * @param order 排序字段
     * @param sort 排序类型
     * @param status 状态
     * @return 文章列表
     */
    @IgnoreParameterNumber
    List<ArticleInfo> selectByPage(@Param("keyword") String keyword,
                                   @Param("category") Integer category,
                                   @Param("tag") String tag,
                                   @Param("year") Integer year,
                                   @Param("month") Integer month,
                                   @Param("order") String order,
                                   @Param("sort") String sort,
                                   @Param("status") Integer status);

    /**
     * 根据状态查询数量
     *
     * @param status 状态代码
     * @return 数量
     */
    Long selectCountByStatus(@Param("status") Integer status);

    /**
     * 查询归档统计，每年每月的数量
     *
     * @return 归档统计列表
     */
    List<HistoryListVO> selectHistoryCount();
}
