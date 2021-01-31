package com.github.gn5r.dynamic.excel.service;

import java.util.List;

import com.github.gn5r.dynamic.excel.dto.FruitsDto;
import com.github.gn5r.dynamic.excel.dto.FruitsSearchFormDto;
import com.github.gn5r.dynamic.excel.entity.果物;
import com.github.gn5r.dynamic.excel.form.FruitForm;
import com.github.gn5r.dynamic.excel.repository.果物Dao;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FruitsService {

    @Autowired
    private 果物Dao fruitDao;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 果物を登録する
     * 
     * @param form 果物登録フォーム
     */
    public void register(FruitForm form) {
        果物 entity = modelMapper.map(form, 果物.class);
        fruitDao.insert(entity);
    }

    /**
     * 果物を検索する
     * 
     * @param form 検索フォーム
     * @return 果物一覧
     */
    public List<FruitsDto> searchByCondition(FruitsSearchFormDto form) {
        return fruitDao.selectByCondition(form);
    }
}
