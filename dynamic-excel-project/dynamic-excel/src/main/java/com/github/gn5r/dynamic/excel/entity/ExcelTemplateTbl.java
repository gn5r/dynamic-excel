package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * Excelテンプレートテーブルエンティティ
 *
 */
@lombok.Data
@Entity(listener = ExcelTemplateTblListener.class)
@Table(name = "EXCEL_TEMPLATE_TBL")
public class ExcelTemplateTbl {

    /** 登録順に自動で割り振られるID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}