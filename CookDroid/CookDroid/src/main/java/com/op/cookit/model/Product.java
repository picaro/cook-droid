package com.op.cookit.model;

import com.op.cookit.model.inner.ProductLocal;

import java.io.Serializable;

/**
 * Created by alex3 on 23.11.13.
 */
public class Product implements Serializable {

    private Integer id;

    private String name;

    private String note;

    private Integer shoplistid;

    private Boolean crossed = false;

    public Product(){
    }

    public Product(ProductLocal productLocal) {
        id = productLocal.getOutid();
        name = productLocal.getName();
        shoplistid = productLocal.getShoplistid();
        note = productLocal.getNote();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getShoplistid() {
        return shoplistid;
    }

    public void setShoplistid(Integer shoplistid) {
        this.shoplistid = shoplistid;
    }

    public Boolean getCrossed() {
        return crossed;
    }

    public void setCrossed(Boolean crossed) {
        this.crossed = crossed;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
