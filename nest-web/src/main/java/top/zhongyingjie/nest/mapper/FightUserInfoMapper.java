package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.nest.domain.FightUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Kong
*/
public interface FightUserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FightUserInfo record);

    int insertSelective(FightUserInfo record);

    FightUserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FightUserInfo record);

    int updateByPrimaryKey(FightUserInfo record);

    /**
     * 查询全部用户
     *
     * @return 用户信息列表
     */
    List<FightUserInfo> selectAll();

    /**
     * 更新访问令牌
     *
     * @param token 令牌
     * @param qqNum QQ号
     * @return 更新条数
     */
    int updateAccessTokenByQQNum(@Param("accessToken")String token,
                                 @Param("qqNum") Integer qqNum);
}
