package top.zhongyingjie.admin.service;

import top.zhongyingjie.common.entity.UserInfo;

import java.util.Map;

public interface IUserService {
    UserInfo getUserInfoByUserName(String userName);

    Map<String, String> getSimpleUserInfoByUserId(Long userId);
}
