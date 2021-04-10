package com.github.gn5r.dynamic.excel.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

@Data
@Entity
public class FruitsDto {

    /** 登録順に自動で割り振られるID */
    @Column(name = "ID")
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
}
