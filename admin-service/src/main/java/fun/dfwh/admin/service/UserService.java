package fun.dfwh.admin.service;

import fun.dfwh.admin.entity.UserInfo;

import java.util.Map;

public interface UserService {
    UserInfo getUserInfoByUserName(String userName);

    Map getSimpleUserInfoByUserId(Long userId);
}
