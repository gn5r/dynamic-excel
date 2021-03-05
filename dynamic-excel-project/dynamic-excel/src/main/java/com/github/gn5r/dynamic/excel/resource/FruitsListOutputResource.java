package com.github.gn5r.dynamic.excel.resource;

import java.io.Serializable;
import java.util.List;

import com.github.gn5r.dynamic.excel.dto.FruitsDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "果物一覧出力リソース")
public class FruitsListOutputResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "テンプレートID")
    private Integer templateId;

    @ApiModelProperty(value = "果物一覧")
    private List<FruitsDto> items;
}
