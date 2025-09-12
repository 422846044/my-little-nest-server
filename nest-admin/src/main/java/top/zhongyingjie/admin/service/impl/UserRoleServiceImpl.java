package top.zhongyingjie.admin.service.impl;

import top.zhongyingjie.admin.entity.UserRoleInfo;
import top.zhongyingjie.admin.mapper.UserRoleInfoMapper;
import top.zhongyingjie.admin.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色服务实现
 *
 * @author Kong
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired(required = false)
    private UserRoleInfoMapper userRoleInfoMapper;

    @Override
    public List<UserRoleInfo> getUserRoleByUserId(Long userId) {
        return userRoleInfoMapper.selectByUserId(userId);
    }
}
