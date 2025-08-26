package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.nest.domain.FightUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 11th
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2024-01-30 21:11:10
* @Entity com.dfwh.djctask.domain.UserInfo
*/
public interface FightUserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FightUserInfo record);

    int insertSelective(FightUserInfo record);

    FightUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FightUserInfo record);

    int updateByPrimaryKey(FightUserInfo record);

    List<FightUserInfo> selectAll();

    int updateAccessTokenByQQNum(@Param("accessToken")String token,
                                 @Param("qqNum") Integer qqNum);
}
