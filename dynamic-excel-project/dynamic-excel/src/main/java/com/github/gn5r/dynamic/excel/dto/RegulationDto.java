package com.github.gn5r.dynamic.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegulationDto {
    
    /** PK */
    private Integer no;

    /** 名称 */
    private String name;

    /** ドキュメントID */
    private String docId;

    /** ドキュメントファイルID */
    private String docFileId;

    /** ドキュメント種類ID */
    private String docTypeId;
}
