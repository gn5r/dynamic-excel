package com.github.gn5r.dynamic.excel.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gn5r.dynamic.excel.annotation.ExcelCell;
import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {

    /**
     * Excelテンプレートの保存ディレクトリ
     * <p>
     * excel/list/
     * </p>
     */
    public static final String LIST_TEMPLATE_DIR = "/excel/list/";

    /**
     * Excelテンプレートprefix
     */
    public static final String LIST_TEMPLATE_PREFIX = "template";

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
                    // テンプレート名を消す
                    fileName = fileName.replaceAll(LIST_TEMPLATE_PREFIX, "");
                    log.debug("数字のみ:" + fileName);
                    SelectBoxDto dto = new SelectBoxDto();
                    dto.setId(Integer.parseInt(fileName));
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
    public static File getTemplate(int id) {
        try {
            String target = LIST_TEMPLATE_PREFIX + id + EXCEL_EXT_NAME;
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
