package top.dfwx.nest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dfwx.common.entity.UserInfo;
import top.dfwx.nest.mapper.UserInfoMapper;
import top.dfwx.nest.service.UserInfoService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Map<String, Object> getSimpleInfo(Long uid) {
        Map<String, Object> data = new HashMap<>();
        String nickName = "",avatar = "";
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(uid);
        if(Objects.nonNull(userInfo)){
            nickName = userInfo.getNickname();
            avatar = userInfo.getAvatar();
        }
        data.put("nickName", nickName);
        data.put("avatar", avatar);
        return data;
    }
}
