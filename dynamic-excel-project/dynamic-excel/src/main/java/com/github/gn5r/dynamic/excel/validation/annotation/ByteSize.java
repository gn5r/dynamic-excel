package com.github.gn5r.dynamic.excel.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.github.gn5r.dynamic.excel.validation.ByteSizeValidation;

/**
 * バイト数バリデーションアノテーション
 * <p>
 * 文字列のバイト数をチェックする
 * </p>
 *
 * @author gn5r
 */
@Documented
@Constraint(validatedBy = { ByteSizeValidation.class })
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteSize {

    String message() default "{com.github.gn5r.dynamic.excel.validation.ByteSize.message}";

    String encoding() default "MS932";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE;
}