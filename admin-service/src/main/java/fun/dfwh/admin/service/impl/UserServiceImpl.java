package fun.dfwh.admin.service.impl;

import fun.dfwh.admin.entity.UserInfo;
import fun.dfwh.admin.mapper.UserInfoMapper;
import fun.dfwh.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        return userInfoMapper.selectByUserName(userName);
    }

    @Override
    public Map getSimpleUserInfoByUserId(Long userId) {
        HashMap<String,String> userInfo = userInfoMapper.selectUserNameAndNickNameAndAvatarByUserId(userId);
        if(userInfo.get("nickName")==null){
            userInfo.put("nickName",userInfo.get("userName"));
        }
        userInfo.remove("userName");
        return userInfo;
    }
}
