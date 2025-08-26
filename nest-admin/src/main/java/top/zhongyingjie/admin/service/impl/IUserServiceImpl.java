package top.zhongyingjie.admin.service.impl;

import top.zhongyingjie.common.entity.UserInfo;
import top.zhongyingjie.admin.mapper.UserInfoMapper;
import top.zhongyingjie.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        return userInfoMapper.selectByUserName(userName);
    }

    @Override
    public Map<String, String> getSimpleUserInfoByUserId(Long userId) {
        Map<String, String> data = new HashMap<>();
        String nickName = "",avatar = "";
        HashMap<String,String> userInfo = userInfoMapper.selectUserNameAndNickNameAndAvatarByUserId(userId);
        if(Objects.nonNull(userInfo)){
            nickName = Optional.ofNullable(userInfo.get("nickName")).orElse(userInfo.get("userName"));
            avatar = userInfo.get("avatar");
        }
        data.put("nickName", nickName);
        data.put("avatar", avatar);
        return data;
    }
}
