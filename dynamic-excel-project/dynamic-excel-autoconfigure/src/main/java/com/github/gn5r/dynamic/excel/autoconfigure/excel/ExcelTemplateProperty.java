package com.github.gn5r.dynamic.excel.autoconfigure.excel;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Excelテンプレートプロパティ
 *
 * @author gn5r
 */
@ConfigurationProperties(prefix = "excel.template")
public class ExcelTemplateProperty {

	/**
	 * Excelテンプレートディレクトリ
	 */
	private String dir;

	/**
	 * Excelテンプレート拡張子
	 */
	private String ext = ".xlsx";

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
}
