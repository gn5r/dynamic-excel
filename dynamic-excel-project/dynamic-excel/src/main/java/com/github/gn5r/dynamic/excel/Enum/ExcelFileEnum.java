package com.github.gn5r.dynamic.excel.Enum;

/**
 * Excelファイル関連のenumクラス
 */
public enum ExcelFileEnum {
    
    /**
     * クラスパスリソース下にあるExcelテンプレートファイルのID prefix
     */
    ID_PREFIX("temp"),

    /**
     * 可変型一覧テンプレートファイルID
     */
    LIST_TEMPLATE_2_ID("temp2");

    private final String value;

    private ExcelFileEnum(String value) {
        this.value = value;
    }

    /**
     * 設定値を取得する
     * 
     * @return 設定値
     */
    public String getValue() {
        return this.value;
    }
}
