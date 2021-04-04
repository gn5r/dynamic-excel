package com.github.gn5r.dynamic.excel.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ファイル種別マスタ登録編集リソース
 * 
 * @author gn5r
 */
@Data
@ApiModel(description = "ファイル種別マスタ登録編集リソース")
public class FileTypeMstFormResource {

    /** ファイル種別名 */
    @ApiModelProperty(name = "種別名")
    private String typeName;
}
