package com.github.gn5r.dynamic.excel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gn5r.dynamic.excel.Enum.ExcelFileEnum;
import com.github.gn5r.dynamic.excel.annotation.ExcelCell;
import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {

    /**
     * Excelテンプレートの保存ディレクトリ
     * <p>
     * /excel/list/
     * </p>
     */
    public static final String LIST_TEMPLATE_DIR = "/excel/list/";

    /**
     * Excelテンプレートprefix
     */
    public static final String LIST_TEMPLATE_PREFIX = "template";

    /**
     * 詳細Excelテンプレートの保存ディレクトリ
     * <p>
     * /excel/detail/
     * </p>
     */
    public static final String DETAIL_TEMPLATE_DIR = "/excel/detail/";

    /**
     * 詳細Excelテンプレートprefix
     */
    public static final String DETAIL_TEMPLATE_PREFIX = "detail";

    /**
     * Excelテンプレート拡張子
     */
    public static final String EXCEL_EXT_NAME = ".xlsx";

    /**
     * 果物一覧のテンプレート一覧を取得する
     * 
     * @return SelectBox用テンプレート一覧
     */
    public static List<SelectBoxDto> getTemplateList() {
        // 返却リスト
        List<SelectBoxDto> list = new ArrayList<>();

        try {
            URI targetDir = new ClassPathResource(LIST_TEMPLATE_DIR).getURI();
            // ファイル一覧
            File dir = new File(targetDir);

            List<File> files = Arrays.asList(dir.listFiles());

            if (CollectionUtils.isNotEmpty(files)) {
                for (File f : files) {
                    log.debug("ファイル名:" + f.getName());
                    // 拡張子を消す
                    String fileName = f.getName().replaceAll(EXCEL_EXT_NAME, "");
                    // テンプレート名を消し数字のみにする
                    String id = ExcelFileEnum.ID_PREFIX.getValue()
                            .concat(fileName.replaceAll(LIST_TEMPLATE_PREFIX, ""));
                    SelectBoxDto dto = new SelectBoxDto();
                    dto.setId(id);
                    dto.setValue(f.getName());
                    list.add(dto);
                }
            }

        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "テンプレートディレクトリの読み込みに失敗しました");
        }

        return list;
    }

    /**
     * 一覧テンプレートを取得する
     * 
     * @param id テンプレート番号
     * @return 一覧テンプレートファイル
     */
    public static File getTemplate(String id) {
        try {
            String target = LIST_TEMPLATE_PREFIX + id.replace(ExcelFileEnum.ID_PREFIX.getValue(), "") + EXCEL_EXT_NAME;
            URI targetDir = new ClassPathResource(LIST_TEMPLATE_DIR + target).getURI();

            // ターゲットファイル
            File targetFile = new File(targetDir);

            if (targetFile.exists()) {
                return targetFile;
            }
        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "テンプレートファイルの読み込みに失敗しました");
        }

        return null;
    }

    /**
     * 詳細テンプレートを取得する
     * 
     * @return 詳細テンプレートファイル
     */
    public static File getDetailTemplate() {
        try {
            String target = DETAIL_TEMPLATE_PREFIX + EXCEL_EXT_NAME;
            URI targetDir = new ClassPathResource(DETAIL_TEMPLATE_DIR + target).getURI();

            File targetFile = new File(targetDir);
            if (targetFile.exists()) {
                return targetFile;
            }

        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "テンプレートファイルの読み込みに失敗しました");
        }

        return null;
    }

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
                    Name cellName = getName(workbook, annotation.tags());
                    if (cellName != null) {
                        AreaReference areaRef = new AreaReference(cellName.getRefersToFormula(),
                                workbook.getSpreadsheetVersion());
                        List<CellReference> cells = Arrays.asList(areaRef.getAllReferencedCells());
                        if (CollectionUtils.isNotEmpty(cells)) {
                            cells.stream().forEach(ref -> {
                                ExcelData data = new ExcelData();
                                data.setRowNum(ref.getRow());
                                data.setCellNum(ref.getCol());
                                data.setCellName(cellName.getNameName());
                                data.setValue(value);
                                list.add(data);
                            });
                        }
                    } else {
                        log.warn(Arrays.toString(annotation.tags()) + "の行が見つかりません");
                    }
                } else {
                    log.warn("変数【" + f.getName() + "】にはアノテーションが付与されていません");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
                log.error("フィールド取得に失敗しました", e);
            }
        }

        return list;
    }

    /**
     * 配列のセル名から {@link Name} を取得する
     * 
     * @param workbook ワークブック
     * @param tags     セル名配列
     * @return {@link Name} オブジェクト
     */
    public static Name getName(Workbook workbook, String... tags) {
        for (String tag : tags) {
            Name name = workbook.getName(tag);
            if (name != null) {
                return name;
            }
        }
        return null;
    }

    /**
     * 
     * @param workbook  ワークブック
     * @param worksheet ワークシート
     * @param source    コピー元の行インデックス
     * @param target    コピー先の行インデックス
     */
    public static void copyRow(Workbook workbook, Sheet worksheet, int source, int target) {
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
    public static void setRowData(Sheet sheet, int rowNum, int cellNum, Object value) {
        Row newRow = sheet.getRow(rowNum);
        Cell newCell = newRow.getCell(cellNum);

        if (value instanceof Integer) {
            newCell.setCellValue(String.valueOf(value));
        } else if (value instanceof String) {
            newCell.setCellValue((String) value);
        }
    }

    /**
     * Excelファイルを指定したディレクトリに保存する
     * 
     * @param file Excelファイル
     * @param path 保存先パス
     */
    public static String registExcelFile(MultipartFile file, String path) {
        String fullPath = "";

        try {
            // ディレクトリを作成
            Path dir = Paths.get(path);
            Files.createDirectories(dir);

            // FileOutputStreamで指定したディレクトリにファイルを出力する
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            fullPath = path.concat(file.getOriginalFilename());
            log.debug("フルパス:" + fullPath);
            FileOutputStream out = new FileOutputStream(fullPath);
            wb.write(out);
            wb.close();
        } catch (EncryptedDocumentException | IOException e) {
            e.printStackTrace();
        }

        return fullPath;
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
