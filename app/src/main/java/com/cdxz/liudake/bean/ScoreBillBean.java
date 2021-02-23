package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class ScoreBillBean implements Serializable {

    private String integral;
    private MyIntegralBean my_integral;
    private List<ListBean> list;

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public MyIntegralBean getMy_integral() {
        return my_integral;
    }

    public void setMy_integral(MyIntegralBean my_integral) {
        this.my_integral = my_integral;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class MyIntegralBean implements Serializable {

        private String wait_integral;
        private String determine_integral;
        private String redmi_integral;
        private String goods_integral;

        public String getWait_integral() {
            return wait_integral;
        }

        public void setWait_integral(String wait_integral) {
            this.wait_integral = wait_integral;
        }

        public String getDetermine_integral() {
            return determine_integral;
        }

        public void setDetermine_integral(String determine_integral) {
            this.determine_integral = determine_integral;
        }

        public String getRedmi_integral() {
            return redmi_integral;
        }

        public void setRedmi_integral(String redmi_integral) {
            this.redmi_integral = redmi_integral;
        }

        public String getGoods_integral() {
            return goods_integral;
        }

        public void setGoods_integral(String goods_integral) {
            this.goods_integral = goods_integral;
        }
    }

    public static class ListBean implements Serializable{

        private String id;
        private String in_out;
        private String type;
        private String amount;
        private String create_time;
        private String remark;
        private String uid;
        private String shop_id;
        private String order_id;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIn_out() {
            return in_out;
        }

        public void setIn_out(String in_out) {
            this.in_out = in_out;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
