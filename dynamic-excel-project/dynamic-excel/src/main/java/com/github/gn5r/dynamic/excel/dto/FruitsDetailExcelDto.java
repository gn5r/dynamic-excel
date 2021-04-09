package com.github.gn5r.dynamic.excel.dto;

import java.time.LocalDateTime;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import lombok.Data;

/**
 * 詳細Excelファイル出力Dto
 */
@Data
public class FruitsDetailExcelDto {

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

    /** 登録日 */
    @ExcelCell(tags = { "登録日" })
    private String createDate;

    /** 更新日 */
    @ExcelCell(tags = { "更新日" })
    private String updateDate;

    /** 論理削除フラグ */
    @ExcelCell(tags = { "論理削除フラグ" })
    private String delFlg;

    /** 発行日 */
    @ExcelCell(tags = { "issueDate", "発行日" })
    private String issueDate;

    /** 発行者 */
    @ExcelCell(tags = { "issuer", "発行者" })
    private String issuer;
}
