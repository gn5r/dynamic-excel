package com.github.gn5r.dynamic.excel.repository;

import java.util.List;

import com.github.gn5r.dynamic.excel.config.AppConfig;
import com.github.gn5r.dynamic.excel.dto.FileTypeMstDto;
import com.github.gn5r.dynamic.excel.entity.FileTypeMst;

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
public interface FileTypeMstDao {

    /**
     * ファイル種別マスタ一覧を取得する
     * 
     * @return ファイル種別マスタDtoリスト
     * @see FileTypeMstDto
     */
    @Select
    List<FileTypeMstDto> selectList();

    /**
     * @param id
     * @return the FileTypeMst entity
     */
    @Select
    FileTypeMst selectById(Integer id);

    /**
     * @param entity
     * @return affected rows
     */
    @Insert
    int insert(FileTypeMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Update
    int update(FileTypeMst entity);

    /**
     * @param entity
     * @return affected rows
     */
    @Delete
    int delete(FileTypeMst entity);
}