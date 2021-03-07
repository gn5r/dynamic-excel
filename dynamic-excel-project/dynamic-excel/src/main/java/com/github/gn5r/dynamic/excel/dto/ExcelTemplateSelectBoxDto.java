package com.github.gn5r.dynamic.excel.dto;

import java.io.File;

import lombok.Data;

@Data
public class ExcelTemplateSelectBoxDto {

    private Integer id;

    private String value;

    private byte[] data;

    private File file;
}
