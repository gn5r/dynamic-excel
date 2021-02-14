package com.github.gn5r.dynamic.excel.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gn5r.dynamic.excel.common.exception.RestRuntimeException;
import com.github.gn5r.dynamic.excel.dto.FruitsDto;
import com.github.gn5r.dynamic.excel.dto.FruitsExcelDto;
import com.github.gn5r.dynamic.excel.dto.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.resource.FruitsListOutputResource;
import com.github.gn5r.dynamic.excel.service.ExcelService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "ExcelRESTコントローラー")
@RestController
@RequestMapping(value = "api/excel")
@CrossOrigin
@Slf4j
public class ExcelRestController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ModelMapper modelMapper;

    private DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> excelImport(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();

        try {
            map = excelService.consoleFileContents(file.getInputStream());
            log.info(map.toString());
        } catch (IOException e) {
            throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelテンプレートの読み込みに失敗しました");
        }

        return ResponseEntity.ok().body(map);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseEntity<?> createList(@RequestBody FruitsListOutputResource form) {

        List<FruitsExcelDto> excelList = new ArrayList<>();

        for (FruitsDto dto : form.getItems()) {
            FruitsExcelDto e = modelMapper.map(dto, FruitsExcelDto.class);
            excelList.add(e);
        }

        FruitsListExcelDto excelDto = new FruitsListExcelDto();
        excelDto.setList(excelList);
        String now = LocalDateTime.now().format(YMD);
        excelDto.setIssueDate(now);
        excelDto.setIssuer("system");

        ByteArrayOutputStream out = excelService.createList(form.getTemplateId(), excelDto);

        return new ResponseEntity<byte[]>(out.toByteArray(), HttpStatus.OK);
    }
}
