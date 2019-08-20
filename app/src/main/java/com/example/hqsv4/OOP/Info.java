package com.example.hqsv4.OOP;

public class Info {

   String info;
   String datafields;
    public Info(String datafields, String info) {
        this.info = info;
        this.datafields = datafields;
    }

    public String getDatafields() {
        return datafields;
    }

    public void setDatafields(String datafields) {
        this.datafields = datafields;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
