package com.github.gn5r.dynamic.excel.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.FruitsDetailExcelDto;
import com.github.gn5r.dynamic.excel.entity.果物;
import com.github.gn5r.dynamic.excel.repository.果物Dao;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;
import com.github.gn5r.dynamic.excel.util.ExcelUtil.ExcelData;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FruitsDetailExcelService {
    
    @Autowired
    private 果物Dao fruitsDao;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * yyyy/MM/dd
     */
    private DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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

}
