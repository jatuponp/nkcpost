package com.nkc.nkcpost.model;

/**
 * Created by Jumpon-pc on 4/10/2558.
 */

import java.util.ArrayList;

public class Mail {
    private String title, number, emsid, datetime;

    public Mail() {
    }

    public Mail(String name, String number, String emsid, String datetime) {
        this.title = name;
        this.number = number;
        this.emsid = emsid;
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmsid() {
        return emsid;
    }

    public void setEmsid(String emsid) {
        this.emsid = emsid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
