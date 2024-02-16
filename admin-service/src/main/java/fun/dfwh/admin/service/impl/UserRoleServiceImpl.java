package fun.dfwh.admin.service.impl;

import fun.dfwh.admin.entity.UserRoleInfo;
import fun.dfwh.admin.mapper.UserRoleInfoMapper;
import fun.dfwh.admin.service.UserRoleService;
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
