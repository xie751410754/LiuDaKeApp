package com.cdxz.liudake.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

/**
 * 商户今日现金结算
 */
public class StoreTodaySettlementCashBean extends Object implements MultiItemEntity {


    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToAccountTime() {
        return toAccountTime;
    }

    public void setToAccountTime(String toAccountTime) {
        this.toAccountTime = toAccountTime;
    }

    /**
     * totalprice : 0.10
     * paytime : 1614928136
     * payment : 支付宝支付
     * name : 李德峰
     * phone : 13388808051
     * to_account_time : 0
     */

    @SerializedName("totalprice")
    private String totalprice;
    @SerializedName("paytime")
    private String paytime;
    @SerializedName("payment")
    private String payment;
    @SerializedName("name")
    private String name;
    @SerializedName("phone")
    private String phone;
    @SerializedName("to_account_time")
    private String toAccountTime;


    @Override
    public int getItemType() {
        return 0;
    }
}

