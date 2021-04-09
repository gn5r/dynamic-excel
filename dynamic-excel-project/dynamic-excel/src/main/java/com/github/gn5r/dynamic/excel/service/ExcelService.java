package com.github.gn5r.dynamic.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.FruitsDetailExcelDto;
import com.github.gn5r.dynamic.excel.dto.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.entity.果物;
import com.github.gn5r.dynamic.excel.enums.経費Enum;
import com.github.gn5r.dynamic.excel.repository.果物Dao;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;
import com.github.gn5r.dynamic.excel.util.ExcelUtil.ExcelData;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelService {

    @Autowired
    private 果物Dao fruitsDao;

    @Autowired
    private ModelMapper modelMapper;

    private CreationHelper helper;

    /**
     * yyyy/MM/dd
     */
    private DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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

    public Object getCell(Sheet sheet, String position, CELL_DESTINATION_TYPE destinationType) {
        CellReference ref = new CellReference(position);
        Row row = sheet.getRow(ref.getRow());
        if (row != null) {
            Cell cell = row.getCell(ref.getCol());
            if (cell != null) {
                CellType type = cell.getCellType();
                // セルが日付型
                if (type == CellType.STRING) {
                    return this.parseDestinationType(cell.getStringCellValue(), destinationType);
                } else if (type == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                        return this.parseDestinationType(format.format(date), destinationType);
                    } else {
                        return this.parseDestinationType(cell.getNumericCellValue(), destinationType);
                    }
                } else if (type == CellType.FORMULA) {
                    FormulaEvaluator evaluator = this.helper.createFormulaEvaluator();
                    CellValue value = evaluator.evaluate(cell);
                    if (value.getCellType() == CellType.STRING) {
                        return this.parseDestinationType(value.getStringValue(), destinationType);
                    } else if (value.getCellType() == CellType.NUMERIC) {
                        return this.parseDestinationType(value.getNumberValue(), destinationType);
                    }
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

    private Object parseDestinationType(Object value, CELL_DESTINATION_TYPE destinationType) {
        if (destinationType != null) {
            Class<?> clazz = destinationType.getType();
            log.info("ターゲットクラス:" + clazz);

            if (clazz.equals(String.class)) {
                return value.toString();
            } else if (clazz.equals(Integer.class)) {
                if (value instanceof Double) {
                    final Double d = (Double) value;
                    return d.intValue();
                } else {
                    return (Integer) value;
                }
            } else if (clazz.equals(Date.class)) {
                return (Date) value;
            } else if (clazz.equals(Double.class)) {
                return (Double) value;
            }
        }
        return value;
    }

    public enum CELL_DESTINATION_TYPE {

        /**
         * String
         */
        STRING(String.class),

        /**
         * Date
         */
        DATE(Date.class),

        /**
         * Integer
         */
        INTEGER(Integer.class),

        /**
         * Double
         */
        DOUBLE(Double.class);

        private Class<?> destinationType;

        private CELL_DESTINATION_TYPE(Class<?> destinationType) {
            this.destinationType = destinationType;
        }

        public Class<?> getType() {
            return this.destinationType;
        }
    }

    public byte[] getTemplateFile(Integer id) {
        File file = ExcelUtil.getTemplate(id);

        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "テンプレートディレクトリの読み込みに失敗しました");
        }
    }

    /**
     * 果物一覧を作成する
     * 
     * @param dto 果物一覧作成用Dto
     * @return ByteArrayOutputStream
     */
    public ByteArrayOutputStream createList(int templateId, FruitsListExcelDto dto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook template = null;

        try {
            File file = ExcelUtil.getTemplate(templateId);

            InputStream is = new FileInputStream(file);
            template = WorkbookFactory.create(is);
            Sheet sheet = template.getSheetAt(0);
            log.info(sheet.getSheetName());

            // 発行日と発行者をセットする
            List<ExcelData> issue = ExcelUtil.getExcelDataList(template, dto);
            if (CollectionUtils.isNotEmpty(issue)) {
                for (ExcelData data : issue) {
                    this.setRowData(sheet, data.getRowNum(), data.getCellNum(), data.getValue());
                }
            }

            for (int i = 0; i < dto.getList().size(); i++) {
                List<ExcelData> dataList = ExcelUtil.getExcelDataList(template, dto.getList().get(i));
                log.debug("1行データ:" + ToStringBuilder.reflectionToString(dataList.toArray(), ToStringStyle.SHORT_PREFIX_STYLE));

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
        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelテンプレートの読み込みに失敗しました");
        } catch (EncryptedDocumentException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "パスワード付きExcelテンプレートの読み込みに失敗しました");
        } finally {
            try {
                template.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelファイルのクローズに失敗しました");
            }
        }

        return out;
    }

    /**
     * 果物詳細Excelファイルを作成する
     * 
     * @param id 果物ID
     * @return 詳細Excelファイルのバイナリデータ
     */
    public byte[] createDetail(Integer id) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Workbook template = null;
        
        final 果物 entity = fruitsDao.selectById(id);

        if(entity == null) {
            throw new RestRuntimeException(HttpStatus.NOT_FOUND, "果物が見つかりません");
        }

        FruitsDetailExcelDto excelDto = modelMapper.map(entity, FruitsDetailExcelDto.class);
        excelDto.setCreateDate(entity.getCreateDate().format(YMD));
        excelDto.setUpdateDate(entity.getUpdateDate().format(YMD));
        excelDto.setDelFlg(entity.getDelFlg() ? "削除" : "未削除");

        final String now = LocalDateTime.now().format(YMD);
        excelDto.setIssueDate(now);
        excelDto.setIssuer("system");

        try {
           File file = ExcelUtil.getDetailTemplate();
           
           InputStream is = new FileInputStream(file);
           template = WorkbookFactory.create(is);
           Sheet sheet = template.getSheetAt(0);
           log.info(sheet.getSheetName());

           // セルデータを取得
           List<ExcelData> dataList = ExcelUtil.getExcelDataList(template, excelDto);
           if(CollectionUtils.isNotEmpty(dataList)) {
               List<Integer> rowNumList = dataList.stream().map(ExcelData::getRowNum).distinct().collect(Collectors.toList());
               for(Integer rowNum : rowNumList) {
                   ExcelUtil.copyRow(template, sheet, rowNum, rowNum);
               }

               for(ExcelData data : dataList) {
                    ExcelUtil.setRowData(sheet, data.getRowNum(), data.getCellNum(), data.getValue());
               }
           }

           template.setSheetName(0, "果物詳細データ");
           template.write(out);
        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelテンプレートの読み込みに失敗しました");
        } finally {
            try {
                template.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelファイルのクローズに失敗しました");
            }
        }

        return out.toByteArray();
    }

    /**
     * 
     * @param workbook  ワークブック
     * @param worksheet ワークシート
     * @param source    コピー元の行インデックス
     * @param target    コピー先の行インデックス
     */
    private void copyRow(Workbook workbook, Sheet worksheet, int source, int target) {
        Row newRow = worksheet.getRow(target);
        Row sourceRow = worksheet.getRow(source);

        if (newRow != null) {
            // コピー先に行が既に存在する場合、１行下にずらす
            worksheet.shiftRows(target, worksheet.getLastRowNum(), 1);
            newRow = worksheet.createRow(target);
        } else {
            // 存在しない場合は作成
            newRow = worksheet.createRow(target);
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
