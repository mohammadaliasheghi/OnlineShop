package com.google.OnlineShop.config.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NumberValidator implements ConstraintValidator<NumberPattern, String> {

    @Override
    public void initialize(NumberPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Pattern.matches("^([0-9]*)$", value);
    }
}
