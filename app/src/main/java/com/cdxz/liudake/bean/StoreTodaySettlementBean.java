package com.cdxz.liudake.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 商户今日结算
 */
public class StoreTodaySettlementBean extends Object implements MultiItemEntity {


    /**
     * hongmi : 2.0000
     * name : Yangdong
     * phone : 18980943666
     * createtime : 1435288911
     * receipt_amount : null
     * to_account_time : null
     */

    private String hongmi;
    private String name;
    private String phone;
    private String createtime = "";
    private String receipt_amount;
    private String to_account_time;

    public String getHongmi() {
        return hongmi;
    }

    public void setHongmi(String hongmi) {
        this.hongmi = hongmi;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Object getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public String getTo_account_time() {
        return to_account_time;
    }

    public void setTo_account_time(String to_account_time) {
        this.to_account_time = to_account_time;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}

