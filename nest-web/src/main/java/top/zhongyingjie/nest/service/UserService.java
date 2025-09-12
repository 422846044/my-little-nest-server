package top.zhongyingjie.nest.service;

import top.zhongyingjie.common.exchandler.GlobalException;
import top.zhongyingjie.nest.domain.FightUserInfo;
import top.zhongyingjie.nest.mapper.FightUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 大乐斗用户信息服务
 *
 * @author Kong
 */
@Service
public class UserService {

    @Autowired(required = false)
    private FightUserInfoMapper fightUserInfoMapper;

    public List<FightUserInfo> getAllUserInfo() {
        return fightUserInfoMapper.selectAll();
    }

    /**
     * 通过QQ号更新token
     *
     * @param token token
     * @param qqNum QQ号
     */
    public void updateTokenByQQNum(String token, Integer qqNum) {
        if (fightUserInfoMapper.updateAccessTokenByQQNum(token, qqNum) != 1) {
            throw new GlobalException("更新失败");
        }
    }

    /**
     * 通过QQ号获取乐斗用户信息
     *
     * @param qqNum QQ号
     * @return 乐斗用户信息
     */
    public FightUserInfo getUserInfoByQQNum(Integer qqNum) {
        return fightUserInfoMapper.selectByPrimaryKey(qqNum.longValue());
    }
}
