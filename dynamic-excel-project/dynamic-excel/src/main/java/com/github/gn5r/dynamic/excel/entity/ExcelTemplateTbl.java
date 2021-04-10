package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * Excelテンプレートテーブルエンティティ
 *
 * @author gn5r
 */
@lombok.Data
@Entity(listener = ExcelTemplateTblListener.class, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "EXCEL_TEMPLATE_TBL")
public class ExcelTemplateTbl {

    /** 登録順に自動で割り振られるID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /** ファイル種別ID */
    protected Integer fileTypeId;

    /** ファイル名 */
    protected String fileName;

    /** ファイルディレクトリ */
    protected String path;

    /** 登録日 */
    protected LocalDateTime createDate;

    /** 更新日 */
    protected LocalDateTime updateDate;

    /** 論理削除フラグ */
    protected Boolean delFlg;
}