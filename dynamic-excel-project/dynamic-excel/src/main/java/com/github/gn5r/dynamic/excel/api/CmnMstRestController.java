package com.github.gn5r.dynamic.excel.api;

import java.util.ArrayList;
import java.util.List;

import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.resource.SelectBoxResource;
import com.github.gn5r.dynamic.excel.service.CmnMstService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "共通マスタRESTコントローラー")
@RestController
@RequestMapping(value = "api/cmnMst/get")
@CrossOrigin
public class CmnMstRestController {

    @Autowired
    private CmnMstService cmnMstService;

    @Autowired
    private ModelMapper modelMapper;

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
}
