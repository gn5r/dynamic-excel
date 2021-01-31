package com.github.gn5r.dynamic.excel.form;

import javax.validation.constraints.NotNull;

import com.github.gn5r.dynamic.excel.validation.annotation.ByteSize;

import lombok.Data;

/**
 * {@link 果物} 登録フォーム
 */
@Data
public class FruitForm {

    /** 果物の漢字名称 */
    @NotNull
    @ByteSize(min = 1, max = 32)
    private String kanjiName;

    /** 果物の一般的に使用される名称(ひらがな/カタカナ) */
    @NotNull
    @ByteSize(min = 1, max = 64)
    private String name;

    /** 属している目名 */
    @ByteSize(min = 0, max = 16)
    private String order;

    /** 属している科名 */
    @ByteSize(min = 0, max = 16)
    private String family;

    /** 属している属名 */
    @ByteSize(min = 0, max = 16)
    private String genus;
}
