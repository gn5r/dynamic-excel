package com.github.gn5r.dynamic.excel.resource;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Excelテンプレートフォームリソース")
public class ExcelTemplateFormResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(name = "Excelテンプレートファイル")
    private List<ExcelTemplateResource> files;
}
