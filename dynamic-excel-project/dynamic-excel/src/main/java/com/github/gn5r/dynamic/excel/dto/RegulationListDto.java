package com.github.gn5r.dynamic.excel.dto;

import java.util.List;

import lombok.Data;

@Data
public class RegulationListDto {

    /** PK */
    private Integer no;

    /** 名称 */
    private String name;

    /** メインファイル */
    private RegulationFileDto mainFile;

    /** サブファイル */
    private RegulationFileDto subFile;

    /** サポートファイルリスト */
    private List<RegulationFileDto> supportFileList;
}
