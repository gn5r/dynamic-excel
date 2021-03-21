package com.github.gn5r.dynamic.excel.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.gn5r.dynamic.excel.validation.ListNotEmptyValidartion;

/**
 * リストの空チェックバリデーション
 * 
 * @author gn5r
 */
@Documented
@Constraint(validatedBy = { ListNotEmptyValidartion.class })
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {

    String message() default "{com.github.gn5r.dynamic.excel.validation.List.NotEmpty.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
