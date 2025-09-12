package top.zhongyingjie.nest.mapper;

import org.apache.ibatis.annotations.Param;
import top.zhongyingjie.entity.UpdatesInfo;

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

    /**
     * 限制数量创建时间倒序查询
     *
     * @param limit 限制条数
     * @return 动态信息列表
     */
    List<UpdatesInfo> selectByLimitOrderByCreateTimeDesc(@Param("limit") int limit);
}
