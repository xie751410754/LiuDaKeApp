package com.cdxz.liudake.bean;

import java.io.Serializable;

public class BankInfoDto implements Serializable {

    /**
     * id : 24
     * uid : 230124
     * account_from : 1
     * account_fee : 0
     * account_number : 1
     * status : 0
     * reason :
     * create_time : 1615341988
     * type : 3
     * type_name : 123456789
     * user_real_name : 养养鱼
     * bank : null
     * sub_bank : null
     * withdraw_type : 1
     * shop_id : 9027
     */

    private String id;
    private String uid;
    private String account_from;
    private String account_fee;
    private String account_number;
    private String status;
    private String reason;
    private String create_time;
    private String type;
    private String type_name;
    private String user_real_name;
    private String bank;
    private String sub_bank;
    private String withdraw_type;
    private String shop_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public String getAccount_fee() {
        return account_fee;
    }

    public void setAccount_fee(String account_fee) {
        this.account_fee = account_fee;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name ==null? "":type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getUser_real_name() {
        return user_real_name==null? "":user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getBank() {
        return bank ==null? "":bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSub_bank() {
        return sub_bank ==null? "":sub_bank;
    }

    public void setSub_bank(String sub_bank) {
        this.sub_bank = sub_bank;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
