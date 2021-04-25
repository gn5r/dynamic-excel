package com.github.gn5r.dynamic.excel.api;

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
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.dto.excel.FruitsExcelDto;
import com.github.gn5r.dynamic.excel.dto.excel.FruitsListExcelDto;
import com.github.gn5r.dynamic.excel.resource.FormDataResource;
import com.github.gn5r.dynamic.excel.resource.FruitsListOutputResource;
import com.github.gn5r.dynamic.excel.service.ExcelService;
import com.github.gn5r.dynamic.excel.service.FruitsDetailExcelService;
import com.github.gn5r.dynamic.excel.service.FruitsListExcelService;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "ExcelRESTコントローラー", description = "Excel出力APIを列挙")
@RestController
@RequestMapping(value = "api/excel")
@CrossOrigin
@Slf4j
public class ExcelRestController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FruitsListExcelService fruitsExcelService;

    @Autowired
    private FruitsDetailExcelService fruitsDetailExcelService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * yyyy/MM/dd
     */
    private DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @ApiOperation(value = "ExcelファイルとリクエストボディのPOSTサンプルAPI", notes = "ファイルとリクエストボディをPOSTし、Excelファイルがあれば特定のセルの値マップを返却します")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "取得に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> excelImport(@RequestParam(value = "files", required = false) MultipartFile[] files,
            FormDataResource formData) {
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

    @ApiOperation(value = "果物一覧をダウンロードする", notes = "画面で表示している果物一覧のExcelファイルをダウンロードします")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "取得に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseEntity<?> createList(@RequestBody @Validated FruitsListOutputResource form, BindingResult bindingResult) {

        log.debug("フォームデータ:" + ToStringBuilder.reflectionToString(form, ToStringStyle.SHORT_PREFIX_STYLE));

        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new RestRuntimeException(HttpStatus.BAD_REQUEST, "パラメータが不正です", fieldErrors);
        }

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

        byte[] bytes = fruitsExcelService.createList(form.getTemplateId(), excelDto);

        return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
    }

    @ApiOperation(value = "果物詳細をダウンロードする", notes = "画面で表示している果物データのExcelファイルをダウンロードします")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "取得に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "detail/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> createList(@ApiParam(name = "id", value = "果物ID") @PathVariable("id") String id) {

        if(StringUtils.isEmpty(id)) {
            throw new RestRuntimeException(HttpStatus.BAD_REQUEST, "果物IDがnullです");
        }

        final byte[] bytes = fruitsDetailExcelService.createDetail(Integer.parseInt(id));

        return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
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

        List<SelectBoxDto> templateList = excelService.getTemplateList();
        if(CollectionUtils.isNotEmpty(templateList)) {
            for(SelectBoxDto dto : templateList) {
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
    public ResponseEntity<?> getTemplateFileById(@PathVariable(name = "id") String id) {

        if(StringUtils.isEmpty(id)) {
            throw new RestRuntimeException(HttpStatus.BAD_REQUEST, "テンプレートIDがnullです");
        }

        byte[] bytes = excelService.getTemplateFile(id);

        return new ResponseEntity<byte[]>(bytes, HttpStatus.OK);
    }
}
