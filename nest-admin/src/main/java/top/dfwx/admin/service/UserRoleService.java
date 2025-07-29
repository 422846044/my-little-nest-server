package top.dfwx.admin.service;

import top.dfwx.admin.entity.UserRoleInfo;

import java.util.List;

public interface UserRoleService {
    List<UserRoleInfo> getUserRoleByUserId(Long userId);
}
