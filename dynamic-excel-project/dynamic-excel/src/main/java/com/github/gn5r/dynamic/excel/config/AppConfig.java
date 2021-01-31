package com.github.gn5r.dynamic.excel.config;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;

public class AppConfig implements Config {

    private Dialect dialect;

    private DataSource dataSource;

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }

    @Override
    public DataSource getDataSource() {
        return this.dataSource;
    }
}