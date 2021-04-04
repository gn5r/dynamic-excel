package com.github.gn5r.dynamic.excel.api;

import java.util.List;

import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;
import com.github.gn5r.dynamic.excel.dto.FileTypeMstDto;
import com.github.gn5r.dynamic.excel.resource.FileTypeMstFormResource;
import com.github.gn5r.dynamic.excel.resource.FileTypeMstResource;
import com.github.gn5r.dynamic.excel.service.FileTypeMstService;
import com.google.common.collect.Lists;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "ファイル種別マスタRESTコントローラー", description = "ファイル種別マスタへの登録更新や削除機能を提供")
@RestController
@RequestMapping(value = "api/fileTypeMst")
@CrossOrigin
public class FileTypeMstRestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileTypeMstService fileTypeMstService;

    @ApiOperation(value = "ファイル種別マスタ一覧の取得", notes = "論理削除されたファイル種別マスタを含めた一覧を返却")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = FileTypeMstResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "get/list", method = RequestMethod.GET)
    public ResponseEntity<?> getList() {
        List<FileTypeMstResource> result = Lists.newArrayList();

        final List<FileTypeMstDto> list = fileTypeMstService.getList();

        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(dto -> {
                FileTypeMstResource e = modelMapper.map(dto, FileTypeMstResource.class);
                result.add(e);
            });
        }

        return new ResponseEntity<List<FileTypeMstResource>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "ファイル種別マスタの取得", notes = "IDからファイル種別マスタを返却")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = FileTypeMstResource.class, responseContainer = "Set"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDetail(@ApiParam(name = "ID", value = "ファイル種別マスタID") @PathVariable("id") Integer id) {
        final FileTypeMstDto dto = fileTypeMstService.getDetail(id);

        FileTypeMstResource result = modelMapper.map(dto, FileTypeMstResource.class);

        return new ResponseEntity<FileTypeMstResource>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "ファイル種別マスタの登録", notes = "入力された情報でファイル種別マスタを登録")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "登録に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public ResponseEntity<?> regist(
            @ApiParam(name = "FileTypeMstFormResource", value = "ファイル種別マスタ登録編集リソース") @RequestBody FileTypeMstFormResource form) {

        final FileTypeMstDto dto = modelMapper.map(form, FileTypeMstDto.class);
        fileTypeMstService.regist(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "ファイル種別マスタの更新", notes = "入力された情報でファイル種別マスタを更新")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "更新に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@ApiParam(name = "ID", value = "ファイル種別マスタID") @PathVariable("id") Integer id,
            @ApiParam(name = "FileTypeMstFormResource", value = "ファイル種別マスタ登録編集リソース") @RequestBody FileTypeMstFormResource form) {

        FileTypeMstDto dto = modelMapper.map(form, FileTypeMstDto.class);
        dto.setId(id);
        fileTypeMstService.update(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "ファイル種別マスタの削除", notes = "IDで取得したファイル種別マスタを論理削除")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "論理削除に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@ApiParam(name = "ID", value = "ファイル種別マスタID") @PathVariable("id") Integer id) {

        fileTypeMstService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "ファイル種別マスタの復活", notes = "IDで取得したファイル種別マスタを論理削除から復活")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "復活に成功しました"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "restore/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> restore(@ApiParam(name = "ID", value = "ファイル種別マスタID") @PathVariable("id") Integer id) {

        fileTypeMstService.restore(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
