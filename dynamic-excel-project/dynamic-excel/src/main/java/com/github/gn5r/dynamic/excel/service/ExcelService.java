package com.github.gn5r.dynamic.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gn5r.dynamic.excel.dto.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.enums.経費Enum;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;
import com.github.gn5r.dynamic.excel.util.ExcelUtil.ExcelData;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelService {

    private CreationHelper helper;

    /**
     * /excel/list/
     */
    private String LIST_TEMPLATE_DIR = "/excel/list/";
    /**
     * template1.xlsx
     */
    private String LIST_TEMPLATE_FINE_NAME = "template1.xlsx";

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

    /**
     * 果物一覧を作成する
     * 
     * @param dto 果物一覧作成用Dto
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream createList(FruitsListExcelDto dto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            InputStream is = new ClassPathResource(this.LIST_TEMPLATE_DIR + this.LIST_TEMPLATE_FINE_NAME)
                    .getInputStream();
            Workbook template = WorkbookFactory.create(is);
            Sheet sheet = template.getSheetAt(0);
            log.info(sheet.getSheetName());

            // 発行日と発行者をセットする
            List<ExcelData> issue = ExcelUtil.getExcelDataList(template, dto);
            if (CollectionUtils.isNotEmpty(issue)) {
                for (ExcelData data : issue) {
                    this.setRowData(sheet, data.getRowNum(), data.getCellNum(), data.getValue());
                }
            }

            // 一覧化するデータの数だけrowを作成する
            for (int i = 0; i < dto.getList().size(); i++) {
                List<ExcelData> dataList = ExcelUtil.getExcelDataList(template, dto.getList().get(i));

                if (i > 0) {
                    int rowNum = dataList.get(0).getRowNum();
                    this.copyRow(template, sheet, rowNum, rowNum + i);
                }

                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (ExcelData data : dataList) {
                        this.setRowData(sheet, data.getRowNum() + i, data.getCellNum(), data.getValue());
                    }
                }
            }
            // 書き込む
            template.setSheetName(0, "果物一覧");
            template.write(out);
        } catch (IOException | EncryptedDocumentException e) {
            e.printStackTrace();
        }

        return out;
    }

    /**
     * 
     * @param workbook          ワークブック
     * @param worksheet         ワークシート
     * @param sourceRowNum      コピー元の行インデックス
     * @param destinationRowNum コピー先の行インデックス
     */
    private void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        if (newRow != null) {
            // コピー先に行が既に存在する場合、１行下にずらす
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
            newRow = worksheet.createRow(destinationRowNum);
        } else {
            // 存在しない場合は作成
            newRow = worksheet.createRow(destinationRowNum);
        }

        // セルの型、スタイル、値などをすべてコピーする
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // コピー元の行が存在しない場合、処理を中断
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // スタイルのコピー
            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            newCell.setCellStyle(newCellStyle);
        }

        // セル結合のコピー
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() + (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                        cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    /**
     * 1行のセルにデータをセットする
     * 
     * @param sheet   シート
     * @param rowNum  行インデックス
     * @param cellNum セルインデックス
     * @param value   セルに書き込む値
     */
    private void setRowData(Sheet sheet, int rowNum, int cellNum, Object value) {
        Row newRow = sheet.getRow(rowNum);
        Cell newCell = newRow.getCell(cellNum);

        if (value instanceof Integer) {
            newCell.setCellValue(String.valueOf(value));
        } else if (value instanceof String) {
            newCell.setCellValue((String) value);
        }
    }
}
