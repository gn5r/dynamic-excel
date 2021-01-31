package com.github.gn5r.dynamic.excel.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * Bean登録名を完全限定名にして登録するクラス
 *
 * @author gn5r
 */
public class FQCNBeanNameGenerator extends AnnotationBeanNameGenerator {

	/**
	 * Bean登録名を完全限定名(パッケージ付き)で登録する
	 *
	 * @param BeanDefinition
	 * @return Bean登録時の完全限定名
	 */
	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		return definition.getBeanClassName();
	}
}
