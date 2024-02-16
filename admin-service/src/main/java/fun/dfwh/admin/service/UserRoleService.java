package fun.dfwh.admin.service;

import fun.dfwh.admin.entity.UserRoleInfo;

import java.util.List;

public interface UserRoleService {
    List<UserRoleInfo> getUserRoleByUserId(Long userId);
}
