package com.github.gn5r.dynamic.excel.api;

import java.util.HashMap;
import java.util.Map;

import com.github.gn5r.dynamic.excel.form.FruitForm;
import com.github.gn5r.dynamic.excel.service.FruitsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping(value = "api")
@Slf4j
public class FruitRestController {

    @Autowired
    private FruitsService fruitsService;

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public ResponseEntity<?> regist(@RequestBody @Validated FruitForm form, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();

        if (bindingResult.hasErrors()) {
            log.error("バリデーションエラー" + String.valueOf(bindingResult.getFieldErrors()));
            map.put("message", "バリデーションエラー");
            map.put("result", bindingResult.getFieldErrors());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        fruitsService.register(form);
        map.put("message", "登録しました");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }
}
