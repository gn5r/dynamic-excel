package com.github.gn5r.dynamic.excel.entity;

import java.time.LocalDateTime;

import org.seasar.doma.jdbc.entity.EntityListener;
import org.seasar.doma.jdbc.entity.PostDeleteContext;
import org.seasar.doma.jdbc.entity.PostInsertContext;
import org.seasar.doma.jdbc.entity.PostUpdateContext;
import org.seasar.doma.jdbc.entity.PreDeleteContext;
import org.seasar.doma.jdbc.entity.PreInsertContext;
import org.seasar.doma.jdbc.entity.PreUpdateContext;

/**
 * 
 */
public class ExcelTemplateTblListener implements EntityListener<ExcelTemplateTbl> {

    @Override
    public void preInsert(ExcelTemplateTbl entity, PreInsertContext<ExcelTemplateTbl> context) {
        LocalDateTime now = LocalDateTime.now();

        entity.setCreateDate(now);
        entity.setUpdateDate(now);
        entity.setDelFlg(false);
    }

    @Override
    public void preUpdate(ExcelTemplateTbl entity, PreUpdateContext<ExcelTemplateTbl> context) {
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdateDate(now);
    }

    @Override
    public void preDelete(ExcelTemplateTbl entity, PreDeleteContext<ExcelTemplateTbl> context) {
    }

    @Override
    public void postInsert(ExcelTemplateTbl entity, PostInsertContext<ExcelTemplateTbl> context) {
    }

    @Override
    public void postUpdate(ExcelTemplateTbl entity, PostUpdateContext<ExcelTemplateTbl> context) {
    }

    @Override
    public void postDelete(ExcelTemplateTbl entity, PostDeleteContext<ExcelTemplateTbl> context) {
    }
}