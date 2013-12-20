package com.op.cookit.model;

import java.io.Serializable;
import java.util.Date;

public class ShopList implements Serializable {

    private String note;

    private Integer date_created;

    private Integer date_kill;

    private String coordinates;


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getDate_created() {
        return date_created;
    }

    public void setDate_created(Integer date_created) {
        this.date_created = date_created;
    }

    public Integer getDate_kill() {
        return date_kill;
    }

    public void setDate_kill(Integer date_kill) {
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
        return note;
    }

}