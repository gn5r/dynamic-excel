package com.github.gn5r.dynamic.excel.resource;

import java.io.File;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "ExcelTemplateリソース")
public class ExcelTemplateResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "ID")
    private Integer id;

    @ApiModelProperty(name = "ファイル名")
    private String value;

    @ApiModelProperty(name = "ファイルバイナリデータ")
    private byte[] data;

    @ApiModelProperty(name = "ファイルデータ")
    private File file;
}
