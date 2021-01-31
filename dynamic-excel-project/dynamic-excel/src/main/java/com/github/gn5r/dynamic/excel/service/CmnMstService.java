package com.github.gn5r.dynamic.excel.service;

import java.util.List;

import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;
import com.github.gn5r.dynamic.excel.repository.SelectBoxDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 共通マスタサービス
 */
@Service
@Transactional
public class CmnMstService {

    @Autowired
    private SelectBoxDao selectBoxDao;

    /**
     * 目名一覧を取得する
     * 
     * @return SelectBox用Dtoの目名リスト
     */
    public List<SelectBoxDto> getOrderList() {
        return selectBoxDao.selectOrderList();
    }

    /**
     * 科名一覧を取得する
     * 
     * @return SelectBox用Dtoの科名リスト
     */
    public List<SelectBoxDto> getFamilyList() {
        return selectBoxDao.selectFamilyList();
    }

    /**
     * 属名一覧を取得する
     * 
     * @return SelectBox用Dtoの属名リスト
     */
    public List<SelectBoxDto> getGenusList() {
        return selectBoxDao.selectGenusList();
    }
}
