package com.github.gn5r.dynamic.excel.autoconfigure.context;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * アプリケーションプロパティ
 *
 * @author gn5r
 */
@ConfigurationProperties(prefix = "application")
public class ApplicationProperty {

	/**
	 * アプリケーションのベースパッケージ
	 */
	private String basePackage;

	/**
	 * アプリケーション名
	 */
	private String name;

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
