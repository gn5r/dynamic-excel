package com.github.gn5r.dynamic.excel.repository;

import java.util.List;

import com.github.gn5r.dynamic.excel.config.AppConfig;
import com.github.gn5r.dynamic.excel.dto.FruitsDto;
import com.github.gn5r.dynamic.excel.dto.FruitsSearchFormDto;
import com.github.gn5r.dynamic.excel.entity.果物;
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
public interface 果物Dao {

    /**
     * @param id
     * @return the 果物 entity
     */
    @Select
    果物 selectById(Integer id);

    /**
     * 果物を条件を指定して検索する
     * 
     * @param condition 検索条件Dto
     * @return 果物エンティティリスト
     */
    @Select
    List<FruitsDto> selectByCondition(FruitsSearchFormDto condition);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(果物 entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(果物 entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(果物 entity);
}