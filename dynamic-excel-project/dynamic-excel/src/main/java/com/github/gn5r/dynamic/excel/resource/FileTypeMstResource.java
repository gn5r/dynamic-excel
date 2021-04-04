package com.github.gn5r.dynamic.excel.resource;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ファイル種別マスタリソース
 * 
 * @author gn5r
 */
@Data
@ApiModel(description = "ファイル種別マスタリソース")
public class FileTypeMstResource {
    
    /** 登録順に自動で割り振られるID */
    @ApiModelProperty(name = "id")
    private Integer id;

    /** ファイル種別名 */
    @ApiModelProperty(name = "種別名")
    private String typeName;

    /** 登録日 */
    @ApiModelProperty(name = "登録日")
    private LocalDateTime createDate;

    /** 更新日 */
    @ApiModelProperty(name = "更新日")
    private LocalDateTime updateDate;

    /** 論理削除フラグ */
    @ApiModelProperty(name = "論理削除フラグ")
    private Boolean delFlg;
}
