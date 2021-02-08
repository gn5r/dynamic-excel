package com.github.gn5r.dynamic.excel.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.gn5r.dynamic.excel.enums.経費Enum;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

    private CreationHelper helper;

    public Map<String, Object> consoleFileContents(InputStream is) {
        Workbook wb = null;
        Map<String, Object> map = new HashMap<>();

        try {
            wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            this.helper = wb.getCreationHelper();

            // 何月度経費
            Object month = this.getCell(sheet, 経費Enum.MONTH.getCellName());
            map.put("何月度経費", month);
            // 作成日
            Object createDate = this.getCell(sheet, 経費Enum.CREATE_DATE.getCellName());
            map.put("作成日", createDate);
            // 従業員名
            Object empName = this.getCell(sheet, 経費Enum.EMPLOYEE_NAME.getCellName());
            map.put("従業員名", empName);
            // 発行日
            Object issueDate = this.getCell(sheet, 経費Enum.ISSUE_DATE.getCellName());
            map.put("発行日", issueDate);
            // 内容
            Object content = this.getCell(sheet, 経費Enum.CONTENT.getCellName());
            map.put("内容", content);
            // 往復区分
            Object roundTripDiv = this.getCell(sheet, 経費Enum.ROUND_TRIP_DIV.getCellName());
            map.put("往復区分", roundTripDiv);
            // 金額
            Object cost = this.getCell(sheet, 経費Enum.COST.getCellName());
            map.put("金額", cost);
            // 合計
            Object total = this.getCell(sheet, 経費Enum.TOTAL.getCellName());
            map.put("合計", total);
            // 結合セル
            Object joinCell = this.getCell(sheet, 経費Enum.JOIN_CELL.getCellName());
            map.put("結合セル", joinCell);

        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public Object getCell(Sheet sheet, String position) {
        CellReference ref = new CellReference(position);
        Row row = sheet.getRow(ref.getRow());
        if (row != null) {
            Cell cell = row.getCell(ref.getCol());
            CellType type = cell.getCellType();
            // セルが日付型
            if (type == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (type == CellType.NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    return format.format(date);
                } else {
                    return cell.getNumericCellValue();
                }
            } else if (type == CellType.FORMULA) {
                FormulaEvaluator evaluator = this.helper.createFormulaEvaluator();
                CellValue value = evaluator.evaluate(cell);
                if (value.getCellType() == CellType.STRING) {
                    return value.getStringValue();
                } else if (value.getCellType() == CellType.NUMERIC) {
                    return value.getNumberValue();
                }
            }
        }
        return null;
    }
}
