package com.github.gn5r.dynamic.excel.dto;

import java.time.LocalDateTime;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

/**
 * ファイル種別マスタDto
 * 
 * @author gn5r
 */
@Data
@Entity
public class FileTypeMstDto {

    /** 登録順に自動で割り振られるID */
    @Column(name = "id")
    private Integer id;

    /** ファイル種別名 */
    @Column(name = "種別名")
    private String typeName;

    /** 登録日 */
    @Column(name = "登録日")
    private LocalDateTime createDate;

    /** 更新日 */
    @Column(name = "更新日")
    private LocalDateTime updateDate;

    /** 論理削除フラグ */
    @Column(name = "論理削除フラグ")
    private Boolean delFlg;
}
