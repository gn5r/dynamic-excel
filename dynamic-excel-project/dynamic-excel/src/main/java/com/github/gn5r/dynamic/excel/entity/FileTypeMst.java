package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * ファイル種別マスタエンティティ
 *
 */
@lombok.Data
@Entity(listener = FileTypeMstListener.class)
@Table(name = "FILE_TYPE_MST")
public class FileTypeMst {

    /** 登録順に自動で割り振られるID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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