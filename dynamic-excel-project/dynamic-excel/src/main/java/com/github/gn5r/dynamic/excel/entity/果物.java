package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;
import org.seasar.doma.jdbc.entity.NamingType;

/**
 * 果物エンティティ
 *
 * @author gn5r
 */
@lombok.Data
@Entity(listener = 果物Listener.class, naming = NamingType.SNAKE_UPPER_CASE)
@Table(name = "果物")
public class 果物 {

    /** 登録順に自動で割り振られるID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /** 果物の漢字名称 */
    protected String kanjiName;

    /** 果物の一般的に使用される名称(ひらがな/カタカナ) */
    protected String name;

    /** 属している目名 */
    protected String order;

    /** 属している科名 */
    protected String family;

    /** 属している属名 */
    protected String genus;

    /** 登録日 */
    protected LocalDateTime createDate;

    /** 更新日 */
    protected LocalDateTime updateDate;

    /** 論理削除フラグ */
    protected Boolean delFlg;
}