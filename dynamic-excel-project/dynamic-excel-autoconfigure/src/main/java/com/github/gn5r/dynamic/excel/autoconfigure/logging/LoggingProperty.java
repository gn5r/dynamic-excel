package com.github.gn5r.dynamic.excel.autoconfigure.logging;

/**
 * ロギングプロパティ
 * 
 * @author gn5r
 */
public class LoggingProperty {

    /**
     * ログディレクトリ
     */
    private String dir = "/var/log";

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }
}
