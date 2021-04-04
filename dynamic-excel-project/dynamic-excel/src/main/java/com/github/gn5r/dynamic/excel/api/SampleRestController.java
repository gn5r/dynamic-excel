package com.github.gn5r.dynamic.excel.api;

import java.util.List;

import com.github.gn5r.dynamic.excel.common.resource.ErrorResource;
import com.github.gn5r.dynamic.excel.dto.RegulationListDto;
import com.github.gn5r.dynamic.excel.service.ListEditService;

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

@Api(tags = "サンプルRESTコントローラー", description = "サンプル機能を提供")
@RestController
@RequestMapping(value = "api/sample")
@CrossOrigin
public class SampleRestController {
    
    @Autowired
    private ListEditService listEditService;

    @ApiOperation(value = "一覧の取得", notes = "まとめた一覧を返却")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "取得に成功しました", response = RegulationListDto.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "サーバー内エラーが発生しました", response = ErrorResource.class, responseContainer = "Set") })
    @RequestMapping(value = "regulationList", method = RequestMethod.GET)
    public ResponseEntity<?> getRegulationList() {
        List<RegulationListDto> list = listEditService.getRegurationList();

        return new ResponseEntity<List<RegulationListDto>>(list, HttpStatus.OK);
    }
}
