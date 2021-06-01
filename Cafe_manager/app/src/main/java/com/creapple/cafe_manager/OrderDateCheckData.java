package com.creapple.cafe_manager;

import java.io.Serializable;

public class OrderDateCheckData {
    private String member_pdt_date;
    private String member_pdt_price_total;

    public String getMember_pdt_date() {
        return member_pdt_date;
    }

    public void setMember_pdt_date(String member_pdt_date) {
        this.member_pdt_date = member_pdt_date;
    }

    public String getMember_pdt_price_total() {
        return member_pdt_price_total;
    }

    public void setMember_pdt_price_total(String  member_pdt_price_total) {
        this.member_pdt_price_total = member_pdt_price_total;
    }

}
