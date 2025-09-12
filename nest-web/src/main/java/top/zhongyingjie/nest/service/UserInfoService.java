package top.zhongyingjie.nest.service;

import java.util.Map;

/**
 * 用户信息服务接口
 *
 * @author Kong
 */
public interface UserInfoService {

    /**
     * 获取用户简单信息
     *
     * @param uid 用户id
     * @return 用户简单信息
     */
    Map<String, Object> getSimpleInfo(Long uid);
}
