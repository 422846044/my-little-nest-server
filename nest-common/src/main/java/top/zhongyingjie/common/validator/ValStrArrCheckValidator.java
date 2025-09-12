package top.zhongyingjie.common.validator;

import top.zhongyingjie.common.annotation.ValStrArrCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义校验字符串数组注解校验器
 *
 * @author Kong
 */
public class ValStrArrCheckValidator implements ConstraintValidator<ValStrArrCheck, String> {
    private List<String> valueCollection;

    @Override
    public void initialize(ValStrArrCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        valueCollection = Arrays.asList(constraintAnnotation.values());
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return valueCollection.contains(value);
    }
}
