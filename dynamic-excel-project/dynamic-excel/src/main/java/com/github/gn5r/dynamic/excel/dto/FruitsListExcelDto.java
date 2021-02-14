package com.github.gn5r.dynamic.excel.dto;

import java.util.List;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import lombok.Data;

@Data
public class FruitsListExcelDto {

    /** 果物一覧 */
    List<FruitsExcelDto> list;

    /** 発行日 */
    @ExcelCell(tags = { "issueDate", "発行日" })
    private String issueDate;

    /** 発行者 */
    @ExcelCell(tags = { "issuer", "発行者" })
    private String issuer;
}