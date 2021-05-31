package com.creapple.cafe_manager;

import java.io.Serializable;

public class OrderPersonalData implements Serializable {
    private String member_pdt_id;
    private String member_pdt_date;
    private String member_pdt_name;
    private String member_pdt_classification;
    private String member_pdt_unit;
    private String member_pdt_price;
    private String member_pdt_stock;
    private String member_pdt_stock_num;
    private String member_pdt_price_total;

    public OrderPersonalData(String member_pdt_name, String member_pdt_classification, String member_pdt_unit, String member_pdt_price, String member_pdt_stock, String member_pdt_stock_num) {
    }

    public OrderPersonalData() {

    }


    public String getMember_pdt_id() {
        return member_pdt_id;
    }

    public String getMember_pdt_name() {
        return member_pdt_name;
    }

    public String getMember_pdt_classification() {
        return member_pdt_classification;
    }

    public String getMember_pdt_unit() {
        return member_pdt_unit;
    }

    public String getMember_pdt_price() {
        return member_pdt_price;
    }

    public String getMember_pdt_stock() { return member_pdt_stock; }

    public String getMember_pdt_stock_num() {
        return member_pdt_stock_num;
    }

    public void setMember_pdt_id(String member_pdt_id) {
        this.member_pdt_id = member_pdt_id;
    }

    public void setMember_pdt_name(String member_pdt_name) {
        this.member_pdt_name = member_pdt_name;
    }

    public void setMember_pdt_classification(String member_pdt_classification) {
        this.member_pdt_classification = member_pdt_classification;
    }

    public void setMember_pdt_unit(String member_pdt_unit) {
        this.member_pdt_unit = member_pdt_unit;
    }

    public void setMember_pdt_price(String member_pdt_price) {
        this.member_pdt_price = member_pdt_price;
    }

    public void setMember_pdt_stock(String member_pdt_stock) {
        this.member_pdt_stock = member_pdt_stock;
    }

    public void setMember_pdt_stock_num(String member_pdt_stock_num) {
        this.member_pdt_stock_num = member_pdt_stock_num;
    }

    public String getMember_pdt_date() {
        return member_pdt_date;
    }

    public void setMember_pdt_date(String member_pdt_date) {
        this.member_pdt_date = member_pdt_date;
    }

    public void setMember_pdt_price_total(String member_pdt_price_total) {
        this.member_pdt_price_total = member_pdt_price_total;
    }

    public String getMember_pdt_price_total() {
        return member_pdt_price_total;
    }
}

