package top.dfwx.admin.service;

import top.dfwx.common.entity.UserInfo;

import java.util.Map;

public interface IUserService {
    UserInfo getUserInfoByUserName(String userName);

    Map<String, String> getSimpleUserInfoByUserId(Long userId);
}
