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
import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;
import com.github.gn5r.dynamic.excel.common.resource.SelectBoxResource;
import com.github.gn5r.dynamic.excel.dto.FruitsDto;
import com.github.gn5r.dynamic.excel.dto.FruitsExcelDto;
import com.github.gn5r.dynamic.excel.dto.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.resource.FormDataResource;
import com.github.gn5r.dynamic.excel.resource.FruitsListOutputResource;
import com.github.gn5r.dynamic.excel.service.ExcelService;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    public ResponseEntity<?> excelImport(@RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestPart(value = "formData") FormDataResource formData) {
        Map<String, Object> map = new HashMap<>();

        log.info(formData.getMessage());

        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null) {
                    try {
                        map = excelService.consoleFileContents(file.getInputStream());
                        log.info(map.toString());
                    } catch (IOException e) {
                        throw new RestRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, "Excelテンプレートの読み込みに失敗しました");
                    }
                }
            }
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

    @ApiOperation(value = "果物一覧テンプレートファイル一覧の取得", notes = "果物一覧テンプレートファイルの一覧をid,valueの形式で取得します")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = SelectBoxResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "get/templateList", method = RequestMethod.GET)
    public ResponseEntity<?> getListTemplateFiles() {
        List<SelectBoxDto> list = ExcelUtil.getTemplateList();
        List<SelectBoxResource> resultList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(list)) {
            for (SelectBoxDto dto : list) {
                SelectBoxResource e = modelMapper.map(dto, SelectBoxResource.class);
                resultList.add(e);
            }
        }

        return new ResponseEntity<List<SelectBoxResource>>(resultList, HttpStatus.OK);
    }

    @ApiOperation(value = "果物一覧テンプレートファイルデータの取得", notes = "IDから果物一覧テンプレートファイルを取得します")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "取得に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "get/templateFile/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTemplateFileById(@PathVariable(name = "id") Integer id) {
        byte[] bytes = excelService.getTemplateFile(id);

        return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
    }
}
