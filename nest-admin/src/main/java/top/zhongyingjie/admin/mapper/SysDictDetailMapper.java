package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.entity.SysDictDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author Kong
 */
public interface SysDictDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDictDetail record);

    int insertSelective(SysDictDetail record);

    SysDictDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictDetail record);

    int updateByPrimaryKey(SysDictDetail record);

    /**
     * 通过字典code和状态排序查询详情映射表列表
     *
     * @param dictCode 字典code
     * @param status   状态
     * @return 详情映射表列表
     */
    List<HashMap> selectByDictCodeAndStatusOrderBySort(@Param("dictCode") String dictCode,
                                                       @Param("status") int status);
    /**
     * 通过字典code查询详情映射表列表
     *
     * @param dictCode 字典code
     * @return 详情映射表列表
     */
    List<HashMap> selectByDictCode(@Param("dictCode") String dictCode);

    /**
     * 根据字典code查询详情code最大值
     *
     * @param dictCode 字典code
     * @return code最大值
     */
    Integer selectOneCodeByDictCodeOrderByCodeDesc(@Param("dictCode") String dictCode);
}
