package top.zhongyingjie.admin.service;

/**
 * 系统参数信息接口
 *
 * @author Kong
 */
public interface SystemService {

    /**
     * 通过key获取value
     *
     * @param key 键
     * @return 值
     */
    String getValue(String key);
}
