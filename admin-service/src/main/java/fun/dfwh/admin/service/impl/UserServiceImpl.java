package fun.dfwh.admin.service.impl;

import fun.dfwh.admin.entity.UserInfo;
import fun.dfwh.admin.mapper.UserInfoMapper;
import fun.dfwh.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo getUserInfoByUserName(String userName) {
        return userInfoMapper.selectByUserName(userName);
    }
}
