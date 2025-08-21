package top.dfwx.nest.service;

import java.util.Map;

public interface UserInfoService {
    Map<String, Object> getSimpleInfo(Long uid);
}
