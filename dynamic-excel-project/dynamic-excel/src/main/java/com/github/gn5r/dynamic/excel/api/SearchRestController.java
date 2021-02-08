package com.github.gn5r.dynamic.excel.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gn5r.dynamic.excel.dto.FruitsDto;
import com.github.gn5r.dynamic.excel.dto.FruitsSearchFormDto;
import com.github.gn5r.dynamic.excel.form.ListSearchForm;
import com.github.gn5r.dynamic.excel.service.FruitsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "果物検索RESTコントローラー")
@RestController
@CrossOrigin
@RequestMapping(value = "api")
@Slf4j
public class SearchRestController {

    @Autowired
    private FruitsService fruitsService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ResponseEntity<?> search(@RequestBody ListSearchForm form) {
        log.debug(form.toString());

        FruitsSearchFormDto dto = modelMapper.map(form, FruitsSearchFormDto.class);
        List<FruitsDto> list = fruitsService.searchByCondition(dto);

        log.debug(list.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("result", list);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
