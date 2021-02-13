package com.github.gn5r.dynamic.excel.dto;

import java.util.List;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import lombok.Data;

@Data
public class FruitsListExcelDto {

    /** 果物一覧 */
    List<FruitsExcelDto> list;

    /** 発行日 */
    @ExcelCell(tags = "issueDate")
    private String issueDate;

    /** 発行者 */
    @ExcelCell(tags = "issuer")
    private String issuer;
}