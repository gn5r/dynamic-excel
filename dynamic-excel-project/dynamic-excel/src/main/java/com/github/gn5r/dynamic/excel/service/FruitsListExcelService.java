package com.github.gn5r.dynamic.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;
import com.github.gn5r.dynamic.excel.util.ExcelUtil.ExcelData;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FruitsListExcelService {

    /**
     * 果物一覧を作成する
     * 
     * @param dto 果物一覧作成用Dto
     * @return ByteArrayOutputStream
     */
    public byte[] createList(int templateId, FruitsListExcelDto dto) {
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
                    ExcelUtil.setRowData(sheet, data.getRowNum(), data.getCellNum(), data.getValue());
                }
            }

            for (int i = 0; i < dto.getList().size(); i++) {
                List<ExcelData> dataList = ExcelUtil.getExcelDataList(template, dto.getList().get(i));
                log.debug("1行データ:" + ToStringBuilder.reflectionToString(dataList.toArray(), ToStringStyle.SHORT_PREFIX_STYLE));

                if (i > 0) {
                    int rowNum = dataList.get(0).getRowNum();
                    ExcelUtil.copyRow(template, sheet, rowNum, rowNum + i);
                }

                if (CollectionUtils.isNotEmpty(dataList)) {
                    for (ExcelData data : dataList) {
                        ExcelUtil.setRowData(sheet, data.getRowNum() + i, data.getCellNum(), data.getValue());
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

        return out.toByteArray();
    }
}
