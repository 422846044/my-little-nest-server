package top.zhongyingjie.nest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zhongyingjie.common.entity.UserInfo;
import top.zhongyingjie.nest.mapper.UserInfoMapper;
import top.zhongyingjie.nest.service.UserInfoService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户信息服务实现
 *
 * @author Kong
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Map<String, Object> getSimpleInfo(Long uid) {
        Map<String, Object> data = new HashMap<>();
        String nickName = "", avatar = "";
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(uid);
        if (Objects.nonNull(userInfo)) {
            nickName = userInfo.getNickname();
            avatar = userInfo.getAvatar();
        }
        data.put("nickName", nickName);
        data.put("avatar", avatar);
        return data;
    }
}
