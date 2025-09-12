package top.zhongyingjie.admin.service;

import top.zhongyingjie.common.entity.UserInfo;

import java.util.Map;

/**
 * 系统用户信息服务接口
 *
 * @author Kong
 */
public interface IUserService {

    /**
     * 通过用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    UserInfo getUserInfoByUserName(String userName);

    /**
     * 通过用户id获取简单用户信息
     *
     * @param userId 用户id
     * @return 用户简单信息
     */
    Map<String, String> getSimpleUserInfoByUserId(Long userId);

}
