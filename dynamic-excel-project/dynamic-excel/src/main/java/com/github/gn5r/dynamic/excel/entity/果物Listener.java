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
 * 果物エンティティListener
 * 
 * @author gn5r
 * @see 果物
 */
public class 果物Listener implements EntityListener<果物> {

    @Override
    public void preInsert(果物 entity, PreInsertContext<果物> context) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateDate(now);
        entity.setUpdateDate(now);
        entity.setDelFlg(false);
    }

    @Override
    public void preUpdate(果物 entity, PreUpdateContext<果物> context) {
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdateDate(now);
    }

    @Override
    public void preDelete(果物 entity, PreDeleteContext<果物> context) {
    }

    @Override
    public void postInsert(果物 entity, PostInsertContext<果物> context) {
    }

    @Override
    public void postUpdate(果物 entity, PostUpdateContext<果物> context) {
    }

    @Override
    public void postDelete(果物 entity, PostDeleteContext<果物> context) {
    }
}