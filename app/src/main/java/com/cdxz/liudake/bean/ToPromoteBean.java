package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class ToPromoteBean implements Serializable {

    private double count;
    private double zhi;
    private double shop;
    private double hong;
    private List<ListBean> list;

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getZhi() {
        return zhi;
    }

    public void setZhi(double zhi) {
        this.zhi = zhi;
    }

    public double getShop() {
        return shop;
    }

    public void setShop(double shop) {
        this.shop = shop;
    }

    public double getHong() {
        return hong;
    }

    public void setHong(double hong) {
        this.hong = hong;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {

        private String uid;
        private String phone;
        private String createtime;
        private String shopnums;
        private String amount;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getShopnums() {
            return shopnums;
        }

        public void setShopnums(String shopnums) {
            this.shopnums = shopnums;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
