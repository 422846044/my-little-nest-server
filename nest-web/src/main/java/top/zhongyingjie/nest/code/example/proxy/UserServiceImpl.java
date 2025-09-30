package top.zhongyingjie.nest.code.example.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户服务实现
 *
 * @author Kong
 */
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void addUser() {
        log.info("添加用户");
    }
}
