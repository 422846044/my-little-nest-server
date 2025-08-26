package top.zhongyingjie.nest.mapper;

import org.apache.ibatis.annotations.Param;
import top.zhongyingjie.common.entity.UserInfo;

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

    UserInfo selectByUserName(@Param("userName") String userName);


    HashMap<String, String> selectUserNameAndNickNameAndAvatarByUserId(@Param("userId") Long userId);
}
