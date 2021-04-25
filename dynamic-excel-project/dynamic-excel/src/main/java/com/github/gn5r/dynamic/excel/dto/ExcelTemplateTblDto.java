package com.github.gn5r.dynamic.excel.dto;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

/**
 * ExcelTemplateTblDto
 */
@Data
@Entity
public class ExcelTemplateTblDto {

    /** 登録順に自動で割り振られるID */
    @Column(name = "ID")
    private Integer id;

    /** ファイル種別ID */
    @Column(name = "FILE_TYPE_ID")
    private Integer fileTypeId;

    /** ファイル名 */
    @Column(name = "ファイル名")
    private String fileName;

    /** ファイルディレクトリ */
    @Column(name = "パス")
    private String path;

    /** 登録日 */
    @Column(name = "登録日")
    private LocalDateTime createDate;

    /** 更新日 */
    @Column(name = "更新日")
    private LocalDateTime updateDate;

    /** 論理削除フラグ */
    @Column(name = "論理削除フラグ")
    private Boolean delFlg;

    /** 種別名 */
    @Column(name = "種別名")
    private String fileTypeName;

    /** PREFIX_PATH */
    @Column(name = "PREFIX_PATH")
    private String prefixPath;
}
