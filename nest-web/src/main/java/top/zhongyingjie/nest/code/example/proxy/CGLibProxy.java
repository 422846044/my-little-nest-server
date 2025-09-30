package top.zhongyingjie.nest.code.example.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 *
 * @author Kong
 */
public class CGLibProxy implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(CGLibProxy.class);

    private final Enhancer enhancer = new Enhancer();

    private Object getProxy(Class<?> clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("前置处理");
        Object result = methodProxy.invokeSuper(o, objects);
        log.info("后置处理");
        return result;
    }

    public static void main(String[] args) {
        CGLibProxy cgLibProxy = new CGLibProxy();
        UserServiceImpl proxy = (UserServiceImpl) cgLibProxy.getProxy(UserServiceImpl.class);
        proxy.addUser();
    }
}
