package com.github.gn5r.dynamic.excel.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.github.gn5r.dynamic.excel.autoconfigure.excel.ExcelTemplateProperty;
import com.github.gn5r.dynamic.excel.dto.ExcelTemplateFormDto;
import com.github.gn5r.dynamic.excel.dto.ExcelTemplateTblDto;
import com.github.gn5r.dynamic.excel.entity.ExcelTemplateTbl;
import com.github.gn5r.dynamic.excel.entity.FileTypeMst;
import com.github.gn5r.dynamic.excel.repository.ExcelTemplateTblDao;
import com.github.gn5r.dynamic.excel.repository.FileTypeMstDao;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ExcelTemplateService {
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExcelTemplateProperty property;

    @Autowired
    private FileTypeMstDao fileTypeMstDao;

    @Autowired
    private ExcelTemplateTblDao excelTemplateTblDao;

    public List<ExcelTemplateTblDto> search(ExcelTemplateTblDto dto) {
        ExcelTemplateTbl condition = modelMapper.map(dto, ExcelTemplateTbl.class);
        return excelTemplateTblDao.selectByCondition(condition);
    }

    public void regist(List<MultipartFile> files, List<ExcelTemplateFormDto> dtoList) {
        for(int i = 0; i < dtoList.size(); i++) {
            ExcelTemplateFormDto dto = dtoList.get(i);
            FileTypeMst fileTypeMst = fileTypeMstDao.selectById(dto.getFileTypeId());

            String path = property.getDir() + fileTypeMst.getPrefixPath() + "/";
            MultipartFile file = files.get(i);
            String fullPath = ExcelUtil.registExcelFile(file, path);

            ExcelTemplateTbl entity = modelMapper.map(dto, ExcelTemplateTbl.class);
            entity.setPath(fullPath);
            excelTemplateTblDao.insert(entity);
        }
    }

    public void delete(Integer id) {
        try {
            ExcelTemplateTbl entity = excelTemplateTblDao.selectById(id);
            entity.setDelFlg(true);
            excelTemplateTblDao.update(entity);

            Path filePath = Paths.get(entity.getPath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
