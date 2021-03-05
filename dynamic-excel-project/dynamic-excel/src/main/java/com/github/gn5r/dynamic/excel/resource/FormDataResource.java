package com.github.gn5r.dynamic.excel.resource;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "フォームデータリソース")
public class FormDataResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "メッセージ", name = "メッセージ")
    private String message;
}