package com.cdxz.liudake.bean;

public class TransferAccountDto {


    /**
     * create_time : 2021-06-04 10:04:53
     * order_id : zz230736to230124t1622772293
     * amount : 0.1
     * tophone : 134****3233
     */

    private String create_time;
    private String order_id;
    private String amount;
    private String tophone;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTophone() {
        return tophone;
    }

    public void setTophone(String tophone) {
        this.tophone = tophone;
    }
}
