package com.op.cookit.model;

import java.io.Serializable;
import java.util.Date;

public class ShopList implements Serializable {

    private String note;

    private Date date_created;

    private Date date_kill;

    private String coordinates;


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_kill() {
        return date_kill;
    }

    public void setDate_kill(Date date_kill) {
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