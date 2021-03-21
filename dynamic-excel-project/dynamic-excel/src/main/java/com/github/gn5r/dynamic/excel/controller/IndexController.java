package com.github.gn5r.dynamic.excel.controller;

import java.util.List;

import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.service.CmnMstService;
import com.github.gn5r.dynamic.excel.util.ExcelUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class IndexController {

    @Autowired
    private CmnMstService cmnMstService;

    private List<SelectBoxDto> orderList() {
        return cmnMstService.getOrderList();
    }

    private List<SelectBoxDto> familyList() {
        return cmnMstService.getFamilyList();
    }

    private List<SelectBoxDto> genusList() {
        return cmnMstService.getGenusList();
    }

    private List<SelectBoxDto> getTemplateList() {
        return ExcelUtil.getTemplateList();
    }

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("orderList", this.orderList());
        model.addAttribute("familyList", this.familyList());
        model.addAttribute("genusList", this.genusList());
        model.addAttribute("templateList", this.getTemplateList());
        return "index";
    }
}
