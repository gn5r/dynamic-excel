package com.github.gn5r.dynamic.excel.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.github.gn5r.dynamic.excel.service.ExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> excelImport(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();

        try {
            map = excelService.consoleFileContents(file.getInputStream());
            log.info(map.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(map);
    }
}
