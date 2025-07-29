package top.dfwx.admin.service.impl;

import top.dfwx.admin.entity.UserRoleInfo;
import top.dfwx.admin.mapper.UserRoleInfoMapper;
import top.dfwx.admin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired(required = false)
    private UserRoleInfoMapper userRoleInfoMapper;
    @Override
    public List<UserRoleInfo> getUserRoleByUserId(Long userId) {
        return userRoleInfoMapper.selectByUserId(userId);
    }
}
