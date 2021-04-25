package com.github.gn5r.dynamic.excel.resource;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Excelテンプレートファイル検索リソース")
public class ExcelTemplateSearchResource implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** ファイル種別ID */
    @ApiModelProperty(name = "ファイル種別ID")
    private Integer fileTypeId;

    /** ファイル名 */
    @ApiModelProperty(name = "ファイル名")
    private String fileName;

    /** ファイルディレクトリ */
    @ApiModelProperty(name = "パス")
    private String path;
}
