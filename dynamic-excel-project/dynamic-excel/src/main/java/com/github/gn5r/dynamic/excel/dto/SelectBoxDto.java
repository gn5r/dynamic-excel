package com.github.gn5r.dynamic.excel.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import lombok.Data;

/**
 * SelectBox用Dto
 */
@Data
@Entity
public class SelectBoxDto {

    /**
     * SelectBox Index
     */
    @Column(name = "id")
    private String id;

    /**
     * SelectBox value
     */
    @Column(name = "value")
    private String value;
}
