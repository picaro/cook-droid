package com.op.cookit.model;

import java.io.Serializable;
import java.lang.Long;
import java.util.List;

public class ShopList implements Serializable {

    private String note;

    private Long date_created;

    private Long date_kill;

    private String coordinates;

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getDate_created() {
        return date_created;
    }

    public void setDate_created(Long date_created) {
        this.date_created = date_created;
    }

    public Long getDate_kill() {
        return date_kill;
    }

    public void setDate_kill(Long date_kill) {
        this.date_kill = date_kill;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }


    @Override
    public String toString() {
        return "ShopList{" +
                "coordinates='" + coordinates + '\'' +
                ", productList=" + productList +
                ", date_kill=" + date_kill +
                ", date_created=" + date_created +
                ", note='" + note + '\'' +
                '}';
    }
}