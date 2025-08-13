package top.dfwx.nest.service;

import top.dfwx.common.exchandler.GlobalException;
import top.dfwx.nest.domain.FightUserInfo;
import top.dfwx.nest.mapper.FightUserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private FightUserInfoMapper fightUserInfoMapper;

    public List<FightUserInfo> getAllUserInfo(){
        return fightUserInfoMapper.selectAll();
    }

    public void updateTokenByQQNum(String token, Integer qqNum) {
        if(fightUserInfoMapper.updateAccessTokenByQQNum(token,qqNum) != 1){
            throw new GlobalException("更新失败");
        }
    }

    public FightUserInfo getUserInfoByQQNum(Integer qqNum) {
        return fightUserInfoMapper.selectByPrimaryKey(qqNum.longValue());
    }
}
