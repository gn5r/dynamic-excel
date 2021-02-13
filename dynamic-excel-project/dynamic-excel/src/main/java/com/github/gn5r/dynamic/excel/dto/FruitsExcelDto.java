package com.github.gn5r.dynamic.excel.dto;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import lombok.Data;

@Data
public class FruitsExcelDto {
    
    /** 登録順に自動で割り振られるID */
    @ExcelCell(tags = "ID")
    private Integer id;

    /** 果物の漢字名称 */
    @ExcelCell(tags = "kanjiName")
    private String kanjiName;

    /** 果物の一般的に使用される名称(ひらがな/カタカナ) */
    @ExcelCell(tags = "name")
    private String name;

    /** 属している目名 */
    @ExcelCell(tags = "order")
    private String order;

    /** 属している科名 */
    @ExcelCell(tags = "family")
    private String family;

    /** 属している属名 */
    @ExcelCell(tags = "genus")
    private String genus;
}
