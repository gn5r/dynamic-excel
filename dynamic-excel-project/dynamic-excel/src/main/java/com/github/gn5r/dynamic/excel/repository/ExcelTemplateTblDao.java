package com.github.gn5r.dynamic.excel.repository;

import java.util.List;

import com.github.gn5r.dynamic.excel.config.AppConfig;
import com.github.gn5r.dynamic.excel.dto.ExcelTemplateTblDto;
import com.github.gn5r.dynamic.excel.entity.ExcelTemplateTbl;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

import org.seasar.doma.boot.ConfigAutowireable;

/**
 */
@ConfigAutowireable
@Dao(config = AppConfig.class)
public interface ExcelTemplateTblDao {

    @Select
    List<ExcelTemplateTblDto> selectByCondition(ExcelTemplateTbl condition);

    /**
     * ファイル種別IDからEXCEL_TEMPLATE_TBLのDtoリストを取得する
     * 
     * @param fileTypeId ファイル種別ID
     * @return ExcelTemplateTblDtoリスト
     * @see ExcelTemplateTblDto
     */
    @Select
    List<ExcelTemplateTblDto> selectByFileTypeId(Integer fileTypeId);

    /**
     * IDからEXCEL_TEMPLATE_TBLのDtoを取得する
     * 
     * @param id ID
     * @return ExcelTemplateTblDto
     * @see ExcelTemplateTblDto
     */
    @Select
    ExcelTemplateTblDto selectByIdForDto(Integer id);

    /**
     * @param id
     * @return the ExcelTemplateTbl entity
     */
    @Select
    ExcelTemplateTbl selectById(Integer id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(ExcelTemplateTbl entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(ExcelTemplateTbl entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(ExcelTemplateTbl entity);
}