package com.github.gn5r.dynamic.excel.form;

import lombok.Data;

/**
 * 果物検索フォーム
 */
@Data
public class ListSearchForm {

    /** 登録ID */
    private Integer id;

    /** 漢字名称 */
    private String kanjiName;

    /** 一般名称(ひらがな/かたかな) */
    private String name;

    /** 目名 */
    private String order;

    /** 科名 */
    private String family;

    /** 属名 */
    private String genus;
}
