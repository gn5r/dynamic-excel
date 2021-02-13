package com.github.gn5r.dynamic.excel.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;

import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {

    /**
     * 行、列、セル名、値を詰め込んだリストを返却する
     * 
     * @param workbook Excelワークブックオブジェクト
     * @param object   {@link ExcelCell} を付与したフィールドを持つクラス
     * @return ExcelDataList
     */
    public static List<ExcelData> getExcelDataList(Workbook workbook, Object object) {
        List<ExcelData> list = new ArrayList<>();

        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        for (Field f : fields) {
            try {
                // private変数にアクセスする
                f.setAccessible(true);
                Object value = f.get(object);

                // ExcelCellアノテーションを取得
                ExcelCell annotation = (ExcelCell) f.getAnnotation(ExcelCell.class);
                if (annotation != null) {
                    Name cellName = workbook.getName(annotation.tags());
                    if (cellName != null) {
                        CellReference ref = new CellReference(cellName.getRefersToFormula());
                        ExcelData data = new ExcelData();
                        data.setRowNum(ref.getRow());
                        data.setCellNum(ref.getCol());
                        data.setCellName(cellName.getNameName());
                        data.setValue(value);
                        list.add(data);
                    } else {
                        log.error(annotation.tags() + "の行が見つかりません");
                    }
                } else {
                    log.error("変数【" + f.getName() + "】にはアノテーションが付与されていません");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("フィールド取得に失敗しました", e);
            }
        }

        return list;
    }

    /**
     * 行インデックス、列インデックス、セルの名前定義、値を保持するクラス
     */
    @Data
    public static class ExcelData {
        /** 行インデックス */
        private int rowNum;

        /** セルインデックス */
        private int cellNum;

        /** セルの名前定義 */
        private String cellName;

        /** セルに書き込む値 */
        private Object value;
    }
}
