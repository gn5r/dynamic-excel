package com.github.gn5r.dynamic.excel.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ExcelCell {

	/**
	 * 名前定義されたセル
	 *
	 * @return 名前定義配列
	 */
	String[] tags() default {};
}
