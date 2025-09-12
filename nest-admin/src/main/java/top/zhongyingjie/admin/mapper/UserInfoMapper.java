package top.zhongyingjie.admin.mapper;

import top.zhongyingjie.common.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
* @author 11th
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-02-21 15:36:02
* @Entity top.dfwx.common.entity.UserInfo
*/
public interface UserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    /**
     * 通过用户名查询用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    UserInfo selectByUserName(@Param("userName") String userName);

    /**
     * 通过用户id查询用户名、昵称、头像
     *
     * @param userId 用户id
     * @return 用户id查询用户名、昵称、头像映射表
     */
    HashMap<String, String> selectUserNameAndNickNameAndAvatarByUserId(@Param("userId") Long userId);
}
