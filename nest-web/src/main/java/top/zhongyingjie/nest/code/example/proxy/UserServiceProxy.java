package top.zhongyingjie.nest.code.example.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用户服务代理
 *
 * @author Kong
 */
public class UserServiceProxy implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(UserServiceProxy.class);

    private final UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("前置处理");
        Object invoke = method.invoke(userService, args);
        log.info("后置处理");
        return invoke;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                userService.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserServiceProxy userServiceProxy = new UserServiceProxy(userService);
        UserService proxy = (UserService) userServiceProxy.getProxy();
        proxy.addUser();
    }
}
