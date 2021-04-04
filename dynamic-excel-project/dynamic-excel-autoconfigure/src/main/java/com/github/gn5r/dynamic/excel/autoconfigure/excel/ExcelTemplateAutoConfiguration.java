package com.github.gn5r.dynamic.excel.autoconfigure.excel;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ExcelTemplateProperty propery;

    @PostConstruct
    public void setTemplateDir() {
        if(SystemUtils.IS_OS_WINDOWS) {
            final String userDir = System.getenv("USERPROFILE");
            final String dir = userDir.concat(propery.getDir());
            propery.setDir(dir);
        }
    }
}
