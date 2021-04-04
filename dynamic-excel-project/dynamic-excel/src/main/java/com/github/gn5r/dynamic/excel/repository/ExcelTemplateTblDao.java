package com.github.gn5r.dynamic.excel.repository;

import com.github.gn5r.dynamic.excel.config.AppConfig;
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