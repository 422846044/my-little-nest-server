package top.dfwx.admin.mapper;

import top.dfwx.admin.entity.SysConfInfo;

/**
* @author Administrator
* @description 针对表【sys_conf_info】的数据库操作Mapper
* @createDate 2025-02-17 16:11:44
* @Entity top.dfwx.admin.entity.SysConfInfo
*/
public interface SysConfInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysConfInfo record);

    int insertSelective(SysConfInfo record);

    SysConfInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfInfo record);

    int updateByPrimaryKey(SysConfInfo record);

    String selectValueByKey(String key);
}
