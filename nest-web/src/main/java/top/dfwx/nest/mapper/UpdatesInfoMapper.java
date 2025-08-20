package top.dfwx.nest.mapper;

import org.apache.ibatis.annotations.Param;
import top.dfwx.entity.UpdatesInfo;

import java.util.List;

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

    List<UpdatesInfo> selectByLimitOrderByCreateTimeDesc(@Param("limit") int limit);
}
