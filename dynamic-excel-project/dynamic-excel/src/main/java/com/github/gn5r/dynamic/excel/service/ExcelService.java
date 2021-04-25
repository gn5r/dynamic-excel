package com.github.gn5r.dynamic.excel.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.enums.経費Enum;
import com.github.gn5r.dynamic.excel.repository.SelectBoxDao;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelService {

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    private SelectBoxDao selectBoxDao;

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
            String createDate = this.getCell(sheet, 経費Enum.CREATE_DATE.getCellName(), String.class);
            map.put("作成日", createDate);
            // 従業員名
            Object empName = this.getCell(sheet, 経費Enum.EMPLOYEE_NAME.getCellName());
            map.put("従業員名", empName);
            // 発行日
            Date issueDate = this.getCell(sheet, 経費Enum.ISSUE_DATE.getCellName(), Date.class);
            map.put("発行日", issueDate);
            // 内容
            Object content = this.getCell(sheet, 経費Enum.CONTENT.getCellName());
            map.put("内容", content);
            // 往復区分
            Object roundTripDiv = this.getCell(sheet, 経費Enum.ROUND_TRIP_DIV.getCellName());
            map.put("往復区分", roundTripDiv);
            // 金額
            String cost = this.getCell(sheet, 経費Enum.COST.getCellName(), String.class);
            map.put("金額", cost);
            // 合計
            Double total = this.getCell(sheet, 経費Enum.TOTAL.getCellName(), Double.class);
            map.put("合計", total);
            // 結合セル
            Object joinCell = this.getCell(sheet, 経費Enum.JOIN_CELL.getCellName());
            map.put("結合セル", joinCell);

        } catch (EncryptedDocumentException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "パスワード付きExcelテンプレートの読み込みに失敗しました");
        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelテンプレートの読み込みに失敗しました");
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelファイルのクローズに失敗しました");
            }
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

    public <D> D getCell(Sheet sheet, String position, Class<D> destinationType) {
        CellReference ref = new CellReference(position);
        Row row = sheet.getRow(ref.getRow());
        if (row != null) {
            Cell cell = row.getCell(ref.getCol());
            CellType type = cell.getCellType();
            if (type == CellType.STRING) {
                // 文字列
                return this.parseDestinationType(cell.getStringCellValue(), destinationType);
            } else if (type == CellType.NUMERIC) {
                // 数値
                if (DateUtil.isCellDateFormatted(cell)) {
                    // 日付
                    return this.parseDestinationType(cell.getDateCellValue(), destinationType);
                } else {
                    return this.parseDestinationType(cell.getNumericCellValue(), destinationType);
                }
            } else if (type == CellType.FORMULA) {
                // 関数
                FormulaEvaluator evaluator = this.helper.createFormulaEvaluator();
                CellValue value = evaluator.evaluate(cell);
                if (value.getCellType() == CellType.STRING) {
                    // 関数の結果が文字列
                    return this.parseDestinationType(value.getStringValue(), destinationType);
                } else if (value.getCellType() == CellType.NUMERIC) {
                    // 関数の結果が数値
                    return this.parseDestinationType(value.getNumberValue(), destinationType);
                }
            }
        }
        return null;
    }

    private <D> D parseDestinationType(Object value, Class<D> destinationType) {
        if (destinationType != null) {
            log.info("value:" + value + " ターゲットクラス:" + destinationType);

            if (destinationType.equals(Integer.class)) {
                if (value instanceof Double) {
                    final Double d = (Double) value;
                    return destinationType.cast(d.intValue());
                } else {
                    return destinationType.cast(value);
                }
            } else if (destinationType.equals(Date.class)) {
                // 変換対象がDateの場合、valueの型によって処理を変える必要がある
                if (value instanceof Date) {
                    // Dateであればそのままキャスト
                    return destinationType.cast(value);
                } else if (value instanceof String) {
                    // Stringであれば一度Dateに変換する
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    try {
                        Date date = format.parse((String) value);
                        return destinationType.cast(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            } else if (destinationType.equals(String.class)) {
                // 変換対象がDateの場合、valueの型によって処理を変える必要がある
                if (value instanceof Date) {
                    // Stringであれば一度Dateに変換する
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String date = format.format(value);
                    return destinationType.cast(date);
                } else {
                    return destinationType.cast(value.toString());
                }
            } else {
                return destinationType.cast(value);
            }
        }

        return null;
    }

    public byte[] getTemplateFile(String id) {
        File file = ExcelUtil.getTemplate(id);

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "テンプレートディレクトリの読み込みに失敗しました");
        }
    }

    public List<SelectBoxDto> getTemplateList() {
        return selectBoxDao.selectTemplateList();
    }
}
