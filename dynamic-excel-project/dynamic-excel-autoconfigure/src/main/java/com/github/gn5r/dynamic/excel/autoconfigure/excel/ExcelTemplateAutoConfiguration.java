package com.github.gn5r.dynamic.excel.autoconfigure.excel;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for {@link ExcelTemplateProperty}
 *
 * @author gn5r
 */
@Configuration
@EnableConfigurationProperties(ExcelTemplateProperty.class)
public class ExcelTemplateAutoConfiguration {

}
