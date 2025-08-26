package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.entity.UpdatesInfo;

/**
* @description 针对表【updates_info(动态信息表)】的数据库操作Mapper
*/
public interface UpdatesInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UpdatesInfo record);

    int insertSelective(UpdatesInfo record);

    UpdatesInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UpdatesInfo record);

    int updateByPrimaryKey(UpdatesInfo record);

}
