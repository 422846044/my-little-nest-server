package top.zhongyingjie.admin.service;

import top.zhongyingjie.admin.entity.UserRoleInfo;

import java.util.List;

/**
 * 用户角色服务接口
 *
 * @author Kong
 */
public interface UserRoleService {

    /**
     * 通过用户id获取用户角色信息
     *
     * @param userId 用户id
     * @return 用户角色信息
     */
    List<UserRoleInfo> getUserRoleByUserId(Long userId);
}
