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
public class FileTypeMstListener implements EntityListener<FileTypeMst> {

    @Override
    public void preInsert(FileTypeMst entity, PreInsertContext<FileTypeMst> context) {
        LocalDateTime now = LocalDateTime.now();

        entity.setCreateDate(now);
        entity.setUpdateDate(now);
        entity.setDelFlg(false);
    }

    @Override
    public void preUpdate(FileTypeMst entity, PreUpdateContext<FileTypeMst> context) {
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdateDate(now);
    }

    @Override
    public void preDelete(FileTypeMst entity, PreDeleteContext<FileTypeMst> context) {
    }

    @Override
    public void postInsert(FileTypeMst entity, PostInsertContext<FileTypeMst> context) {
    }

    @Override
    public void postUpdate(FileTypeMst entity, PostUpdateContext<FileTypeMst> context) {
    }

    @Override
    public void postDelete(FileTypeMst entity, PostDeleteContext<FileTypeMst> context) {
    }
}