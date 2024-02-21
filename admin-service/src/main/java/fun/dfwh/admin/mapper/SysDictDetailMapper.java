package fun.dfwh.admin.mapper;

import fun.dfwh.admin.entity.SysDictDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
* @author 11th
* @description 针对表【sys_dict_detail】的数据库操作Mapper
* @createDate 2024-02-15 15:55:25
* @Entity fun.dfwh.admin.entity.SysDictDetail
*/
public interface SysDictDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDictDetail record);

    int insertSelective(SysDictDetail record);

    SysDictDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDictDetail record);

    int updateByPrimaryKey(SysDictDetail record);

    List<HashMap> selectByDictCodeAndStatusOrderBySort(@Param("dictCode") String dictCode,
                                                       @Param("status") int status);

    List<HashMap> selectByDictCode(@Param("dictCode") String dictCode);
}
