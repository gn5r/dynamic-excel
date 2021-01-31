package com.github.gn5r.dynamic.excel.validation;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.gn5r.dynamic.excel.validation.annotation.ByteSize;

/**
 * {@link ByteSize}用バリデーションクラス
 *
 * @author gn5r
 */
public class ByteSizeValidation implements ConstraintValidator<ByteSize, CharSequence> {

    private String message;
    private String encoding;
    private int min;
    private int max;

    @Override
    public void initialize(ByteSize annotation) {
        this.message = annotation.message();
        this.encoding = annotation.encoding();
        this.min = annotation.min();
        this.max = annotation.max();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;

        try {
            final int size = String.valueOf(value).getBytes(this.encoding).length;

            if (size >= this.min && size <= this.max) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
}