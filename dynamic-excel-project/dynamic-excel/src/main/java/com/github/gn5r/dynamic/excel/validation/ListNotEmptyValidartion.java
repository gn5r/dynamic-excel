package com.github.gn5r.dynamic.excel.validation;

import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.gn5r.dynamic.excel.validation.annotation.NotEmpty;

import org.springframework.util.CollectionUtils;

/**
 * {@link NotEmpty}用バリデーションクラス
 * 
 * @author gn5r
 */
public class ListNotEmptyValidartion implements ConstraintValidator<NotEmpty, List<?>> {

    private String message;

    @Override
    public void initialize(NotEmpty annotation) {
        this.message = annotation.message();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (!Objects.isNull(value)) {
            if (!CollectionUtils.isEmpty(value)) {
                return true;
            }
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }

}
