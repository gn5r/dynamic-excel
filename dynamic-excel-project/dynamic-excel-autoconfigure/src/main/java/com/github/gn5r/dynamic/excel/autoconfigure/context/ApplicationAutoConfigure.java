package com.github.gn5r.dynamic.excel.autoconfigure.context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for {@link ApplicationProperty}
 *
 * @author gn5r
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperty.class)
public class ApplicationAutoConfigure {

}
