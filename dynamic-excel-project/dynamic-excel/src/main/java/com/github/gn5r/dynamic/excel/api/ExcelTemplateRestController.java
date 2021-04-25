package com.github.gn5r.dynamic.excel.api;

import java.util.List;

import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;
import com.github.gn5r.dynamic.excel.dto.ExcelTemplateFormDto;
import com.github.gn5r.dynamic.excel.dto.ExcelTemplateTblDto;
import com.github.gn5r.dynamic.excel.resource.ExcelTemplateFormResource;
import com.github.gn5r.dynamic.excel.resource.ExcelTemplateResource;
import com.github.gn5r.dynamic.excel.resource.ExcelTemplateSearchResource;
import com.github.gn5r.dynamic.excel.service.ExcelTemplateService;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "ExcelテンプレートRESTコントローラー", description = "Excelテンプレートを操作するAPIを列挙")
@RestController
@RequestMapping(value = "api/template")
@CrossOrigin
public class ExcelTemplateRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ExcelTemplateService excelTemplateService;

    @ApiOperation(value = "Excelテンプレートファイルの検索", notes = "指定した条件で帳票出力用のExcelファイルテンプレートを検索")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = ExcelTemplateResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ResponseEntity<?> searchList(@RequestBody ExcelTemplateSearchResource condition) {

        ExcelTemplateTblDto dto = modelMapper.map(condition, ExcelTemplateTblDto.class);
        List<ExcelTemplateTblDto> list = excelTemplateService.search(dto);

        List<ExcelTemplateResource> resultList = Lists.newArrayList();
        list.stream().forEach(result -> {
            ExcelTemplateResource e = modelMapper.map(result, ExcelTemplateResource.class);
            resultList.add(e);
        });

        return new ResponseEntity<List<ExcelTemplateResource>>(resultList, HttpStatus.OK);
    }

    @ApiOperation(value = "Excelテンプレートファイルのアップロード", notes = "帳票出力用のExcelファイルテンプレートをアップロード")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "ファイルアップロードに成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "upload/files", method = RequestMethod.POST)
    public ResponseEntity<?> fileUpload(@RequestPart("files") List<MultipartFile> files,
            @RequestPart("form") ExcelTemplateFormResource form) {
        files.stream().forEach(file -> log
                .debug("ファイルデータ:" + ToStringBuilder.reflectionToString(file, ToStringStyle.SHORT_PREFIX_STYLE)));
        log.debug("フォームデータ:" + ToStringBuilder.reflectionToString(form, ToStringStyle.SHORT_PREFIX_STYLE));

        List<ExcelTemplateFormDto> dtoList = Lists.newArrayList();

        form.getFiles().stream().forEach(res -> {
            ExcelTemplateFormDto dto = modelMapper.map(res, ExcelTemplateFormDto.class);
            dtoList.add(dto);
        });

        excelTemplateService.regist(files, dtoList);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Excelテンプレートファイルの論理削除", notes = "帳票出力用のExcelファイルテンプレートを論理削除")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "削除に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "delete/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> deleteFile(@PathVariable(name = "id") String id) {
        excelTemplateService.delete(Integer.parseInt(id));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
