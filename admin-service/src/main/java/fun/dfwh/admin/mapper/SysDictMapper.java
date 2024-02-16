package fun.dfwh.admin.mapper;

import fun.dfwh.admin.entity.SysDict;
import org.apache.ibatis.annotations.Param;

/**
* @author 11th
* @description 针对表【sys_dict】的数据库操作Mapper
* @createDate 2024-02-15 15:55:12
* @Entity fun.dfwh.admin.entity.SysDict
*/
public interface SysDictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    Integer select1ByDictCodeAndStatus(@Param("dictCode") String dictCode,
                                       @Param("status") int status);
}
