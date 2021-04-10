package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * ファイル種別マスタエンティティ
 *
 * @author gn5r
 */
@lombok.Data
@Entity(listener = FileTypeMstListener.class, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "FILE_TYPE_MST")
public class FileTypeMst {

    /** 登録順に自動で割り振られるID */
    @Id
    protected Integer id;

    /** ファイル種別名 */
    protected String typeName;

    /** 登録日 */
    protected LocalDateTime createDate;

    /** 更新日 */
    protected LocalDateTime updateDate;

    /** 論理削除フラグ */
    protected Boolean delFlg;

    /** ファイル種別ごとの保存先パスの接頭辞 */
    protected String prefixPath;
}