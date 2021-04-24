package com.github.gn5r.dynamic.excel.dto.excel;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import lombok.Data;

@Data
public class FruitsExcelDto {

    /** 登録順に自動で割り振られるID */
    @ExcelCell(tags = "ID")
    private Integer id;

    /** 果物の漢字名称 */
    @ExcelCell(tags = { "kanjiName", "漢字名称" })
    private String kanjiName;

    /** 果物の一般的に使用される名称(ひらがな/カタカナ) */
    @ExcelCell(tags = { "name", "一般名称" })
    private String name;

    /** 属している目名 */
    @ExcelCell(tags = { "order", "目名" })
    private String order;

    /** 属している科名 */
    @ExcelCell(tags = { "family", "科名" })
    private String family;

    /** 属している属名 */
    @ExcelCell(tags = { "genus", "属名" })
    private String genus;
}
