package com.github.gn5r.dynamic.excel.repository;

import java.util.List;

import com.github.gn5r.dynamic.excel.config.AppConfig;
import com.github.gn5r.dynamic.excel.dto.SelectBoxDto;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

/**
 * SelectBox用のDaoクラス
 */
@ConfigAutowireable
@Dao(config = AppConfig.class)
public interface SelectBoxDao {

    /**
     * 目名の一覧を取得する
     * 
     * @return SelectBox用Dtoの目名リスト
     */
    @Select
    public List<SelectBoxDto> selectOrderList();

    /**
     * 科名の一覧を取得する
     * 
     * @return SelectBox用Dtoの科名リスト
     */
    @Select
    public List<SelectBoxDto> selectFamilyList();

    /**
     * 属名の一覧を取得する
     * 
     * @return SelectBox用Dtoの属名リスト
     */
    @Select
    public List<SelectBoxDto> selectGenusList();

    /**
     * ファイル種別の一覧を取得する
     * 
     * @return SelectBox用Dtoのファイル種別リスト
     */
    @Select
    public List<SelectBoxDto> selectFileTypeList();

    /**
     * アップロードしたExcelテンプレートの一覧を取得する
     * 
     * @return SelectBox用DtoのExcelテンプレートリスト
     */
    @Select
    public List<SelectBoxDto> selectTemplateList();
}