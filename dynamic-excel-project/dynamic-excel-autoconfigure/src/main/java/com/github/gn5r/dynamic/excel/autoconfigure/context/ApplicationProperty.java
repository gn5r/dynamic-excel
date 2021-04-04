package com.github.gn5r.dynamic.excel.autoconfigure.context;

import com.github.gn5r.dynamic.excel.autoconfigure.logging.LoggingProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * アプリケーションプロパティ
 *
 * @author gn5r
 */
@Data
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

	/**
	 * ログディレクトリ
	 */
	private LoggingProperty logging;

}
