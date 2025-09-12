package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.admin.entity.SysConfInfo;

/**
* @author Kong
*/
public interface SysConfInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysConfInfo record);

    int insertSelective(SysConfInfo record);

    SysConfInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysConfInfo record);

    int updateByPrimaryKey(SysConfInfo record);

    /**
     * 查询值通过键
     *
     * @param key 键
     * @return 值
     */
    String selectValueByKey(String key);
}
