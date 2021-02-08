package com.github.gn5r.dynamic.excel.enums;

public enum 経費Enum {

    /**
     * 何年何月度経費
     */
    MONTH(0, 1, "B1"),
    /**
     * 作成日
     */
    CREATE_DATE(0, 3, "D1"),
    /**
     * 従業員名
     */
    EMPLOYEE_NAME(3, 3, "D3"),
    /**
     * 発行日
     */
    ISSUE_DATE(6, 0, "A7"),
    /**
     * 内容
     */
    CONTENT(6, 1, "B7"),
    /**
     * 往復区分
     */
    ROUND_TRIP_DIV(6, 2, "C7"),
    /**
     * 金額
     */
    COST(6, 3, "D7"),
    /**
     * 合計
     */
    TOTAL(10, 3, "D11"),
    /**
     * 結合セル
     */
    JOIN_CELL(0, 5, "F1");

    private int rowNo;
    private int cellNo;
    private String cellName;

    private 経費Enum(int rowNo, int cellNo, String cellName) {
        this.rowNo = rowNo;
        this.cellNo = cellNo;
        this.cellName = cellName;
    }

    public int getRowNo() {
        return this.rowNo;
    }

    public int getCellNo() {
        return this.cellNo;
    }

    public String getCellName() {
        return this.cellName;
    }
}
