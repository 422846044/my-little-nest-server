package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.admin.entity.SysDict;
import org.apache.ibatis.annotations.Param;

/**
* @author 11th
* @description 针对表【sys_dict】的数据库操作Mapper
* @createDate 2024-02-15 15:55:12
* @Entity top.zhongyingjie.admin.entity.SysDict
*/
public interface SysDictMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    /**
     * 判断是否存在值
     *
     * @param dictCode 字典code
     * @param status 状态
     * @return 1
     */
    Integer select1ByDictCodeAndStatus(@Param("dictCode") String dictCode,
                                       @Param("status") int status);
}
