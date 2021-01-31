package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

/**
 * 果物エンティティ
 *
 */
@lombok.Data
@Entity(listener = 果物Listener.class)
@Table(name = "果物")
public class 果物 {

    /** 登録順に自動で割り振られるID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** 果物の漢字名称 */
    @Column(name = "漢字名称")
    private String kanjiName;

    /** 果物の一般的に使用される名称(ひらがな/カタカナ) */
    @Column(name = "一般名称")
    private String name;

    /** 属している目名 */
    @Column(name = "目")
    private String order;

    /** 属している科名 */
    @Column(name = "科")
    private String family;

    /** 属している属名 */
    @Column(name = "属")
    private String genus;

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