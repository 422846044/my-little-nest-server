package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.admin.entity.UserRoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 11th
 * @description 针对表【user_role_info】的数据库操作Mapper
 * @createDate 2024-02-10 19:03:20
 * @Entity top.dfwx.admin.entity.UserRoleInfo
 */
public interface UserRoleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserRoleInfo record);

    int insertSelective(UserRoleInfo record);

    UserRoleInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleInfo record);

    int updateByPrimaryKey(UserRoleInfo record);

    /**
     * 通过用户id查询角色信息列表
     *
     * @param userId 用户id
     * @return 角色信息列表
     */
    List<UserRoleInfo> selectByUserId(@Param("userId") Long userId);
}
