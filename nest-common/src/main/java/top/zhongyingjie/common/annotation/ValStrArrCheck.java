package top.zhongyingjie.common.annotation;

import top.zhongyingjie.common.validator.ValStrArrCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * 自定义校验字符串数组注解
 *
 * @author Kong
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ValStrArrCheckValidator.class})
public @interface ValStrArrCheck {

    /**
     * 设置消息
     *
     * @return 消息
     */
    String message() default "";

    /**
     * 设置分组
     *
     * @return 分组
     */
    Class<?>[] groups() default {};

    /**
     * 设置类
     *
     * @return 类
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 设置值数组
     *
     * @return 值数组
     */
    String[] values();

}
