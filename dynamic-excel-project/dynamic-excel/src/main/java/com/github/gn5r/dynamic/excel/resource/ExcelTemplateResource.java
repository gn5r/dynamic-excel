package com.github.gn5r.dynamic.excel.resource;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "ExcelTemplateリソース")
public class ExcelTemplateResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 登録順に自動で割り振られるID */
    @ApiModelProperty(name = "ID")
    private Integer id;

    /** ファイル種別ID */
    @ApiModelProperty(name = "ファイル種別ID")
    private Integer fileTypeId;

    /** ファイル名 */
    @ApiModelProperty(name = "ファイル名")
    private String fileName;

    /** ファイルディレクトリ */
    @ApiModelProperty(name = "パス")
    private String path;

    /** 登録日 */
    @ApiModelProperty(name = "登録日")
    private LocalDateTime createDate;

    /** 更新日 */
    @ApiModelProperty(name = "更新日")
    private LocalDateTime updateDate;

    /** 論理削除フラグ */
    @ApiModelProperty(name = "論理削除フラグ")
    private Boolean delFlg;

    /** 種別名 */
    @ApiModelProperty(name = "種別名")
    private String fileTypeName;

    /** PREFIX_PATH */
    @ApiModelProperty(name = "PREFIX_PATH")
    private String prefixPath;


}
