package top.zhongyingjie.admin.service;

import top.zhongyingjie.admin.entity.UserRoleInfo;

import java.util.List;

public interface UserRoleService {
    List<UserRoleInfo> getUserRoleByUserId(Long userId);
}
