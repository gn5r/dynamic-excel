package com.github.gn5r.dynamic.excel.common.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SelectBoxリソース
 */
@Data
@ApiModel(description = "SelectBoxリソース")
public class SelectBoxResource {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "value")
    private String value;
}
