package top.zhongyingjie.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 自定义注解，忽略参数个数校验
 *
 * @author Kong
 */
@Target(ElementType.METHOD)
public @interface IgnoreParameterNumber {
}
