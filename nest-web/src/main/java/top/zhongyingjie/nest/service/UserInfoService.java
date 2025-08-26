package top.zhongyingjie.nest.service;

import java.util.Map;

public interface UserInfoService {
    Map<String, Object> getSimpleInfo(Long uid);
}
