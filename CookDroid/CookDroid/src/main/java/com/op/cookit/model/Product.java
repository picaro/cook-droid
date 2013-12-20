package com.op.cookit.model;

import java.io.Serializable;

/**
 * Created by alex3 on 23.11.13.
 */
public class Product implements Serializable {

    private String name;

    private String note;

    private Integer shoplistid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
