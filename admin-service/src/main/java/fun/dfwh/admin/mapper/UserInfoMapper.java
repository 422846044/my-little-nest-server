package fun.dfwh.admin.mapper;

import fun.dfwh.admin.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author 11th
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-02-10 18:17:31
* @Entity fun.dfwh.admin.entity.UserInfo
*/
public interface UserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo selectByUserName(@Param("userName") String userName);
}
