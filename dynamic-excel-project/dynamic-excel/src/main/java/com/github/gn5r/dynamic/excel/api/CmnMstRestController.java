package com.github.gn5r.dynamic.excel.api;

import java.util.ArrayList;
import java.util.List;

import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;
import com.github.gn5r.dynamic.excel.common.resource.SelectBoxResource;
import com.github.gn5r.dynamic.excel.service.CmnMstService;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "共通マスタRESTコントローラー", description = "汎用共通機能のAPIを列挙")
@RestController
@RequestMapping(value = "api/cmnMst/get")
@CrossOrigin
public class CmnMstRestController {

    @Autowired
    private CmnMstService cmnMstService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "目名一覧の取得", notes = "登録されている目名の一覧をid,valueの形式で取得します")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = SelectBoxResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "orderList", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderList() {
        List<SelectBoxResource> result = new ArrayList<>();
        List<SelectBoxDto> list = cmnMstService.getOrderList();

        if (!list.isEmpty()) {
            for (SelectBoxDto dto : list) {
                SelectBoxResource resource = modelMapper.map(dto, SelectBoxResource.class);
                result.add(resource);
            }
        }
        return new ResponseEntity<List<SelectBoxResource>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "科名一覧の取得", notes = "登録されている科名の一覧をid,valueの形式で取得します")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = SelectBoxResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "familyList", method = RequestMethod.GET)
    public ResponseEntity<?> getFamilyList() {
        List<SelectBoxResource> result = new ArrayList<>();
        List<SelectBoxDto> list = cmnMstService.getFamilyList();

        if (!list.isEmpty()) {
            for (SelectBoxDto dto : list) {
                SelectBoxResource resource = modelMapper.map(dto, SelectBoxResource.class);
                result.add(resource);
            }
        }
        return new ResponseEntity<List<SelectBoxResource>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "属名一覧の取得", notes = "登録されている属名の一覧をid,valueの形式で取得します")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = SelectBoxResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "genusList", method = RequestMethod.GET)
    public ResponseEntity<?> getGenusList() {
        List<SelectBoxResource> result = new ArrayList<>();
        List<SelectBoxDto> list = cmnMstService.getGenusList();

        if (!list.isEmpty()) {
            for (SelectBoxDto dto : list) {
                SelectBoxResource resource = modelMapper.map(dto, SelectBoxResource.class);
                result.add(resource);
            }
        }
        return new ResponseEntity<List<SelectBoxResource>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "果物一覧テンプレートファイル一覧の取得", notes = "果物一覧テンプレートファイルの一覧をid,valueの形式で取得します")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = SelectBoxResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "templateList", method = RequestMethod.GET)
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
}
