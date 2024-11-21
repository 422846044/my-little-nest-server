package fun.dfwh.common.validator;

import fun.dfwh.common.annotation.ValStrArrCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

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
