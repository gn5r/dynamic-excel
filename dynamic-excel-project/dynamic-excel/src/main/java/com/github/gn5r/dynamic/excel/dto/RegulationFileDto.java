package com.github.gn5r.dynamic.excel.dto;

import lombok.Data;

@Data
public class RegulationFileDto {

    /** ドキュメントID */
    private String docId;

    /** ドキュメントファイルID */
    private String docFileId;

    /** ドキュメント種類ID */
    private String docTypeId;
}
