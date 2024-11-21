package fun.dfwh.admin.service;

import fun.dfwh.admin.entity.UserInfo;

import java.util.Map;

public interface IUserService {
    UserInfo getUserInfoByUserName(String userName);

    Map<String, String> getSimpleUserInfoByUserId(Long userId);
}
