package com.cdxz.liudake.bean;

import java.io.Serializable;

public class GoodsBean implements Serializable {

    private String id;
    private String name;
    private String logo;
    private String gold;
    private String description;
    private String saleprice;
    private String memberprice;
    private String salescount;
    private String false_salescount;
    private String stock;
    private String status;
    private String compare_desc;
    private String compare_url;
    private String integral;
    private String orginalprice;
    private ShopInfoBean shop_info;
    private int is_close;
    private int is_size;
    private int cart_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(String memberprice) {
        this.memberprice = memberprice;
    }

    public String getSalescount() {
        return salescount;
    }

    public void setSalescount(String salescount) {
        this.salescount = salescount;
    }

    public String getFalse_salescount() {
        return false_salescount;
    }

    public void setFalse_salescount(String false_salescount) {
        this.false_salescount = false_salescount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompare_desc() {
        return compare_desc;
    }

    public void setCompare_desc(String compare_desc) {
        this.compare_desc = compare_desc;
    }

    public String getCompare_url() {
        return compare_url;
    }

    public void setCompare_url(String compare_url) {
        this.compare_url = compare_url;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getOrginalprice() {
        return orginalprice;
    }

    public void setOrginalprice(String orginalprice) {
        this.orginalprice = orginalprice;
    }

    public ShopInfoBean getShop_info() {
        return shop_info;
    }

    public void setShop_info(ShopInfoBean shop_info) {
        this.shop_info = shop_info;
    }

    public int getIs_close() {
        return is_close;
    }

    public void setIs_close(int is_close) {
        this.is_close = is_close;
    }

    public int getIs_size() {
        return is_size;
    }

    public void setIs_size(int is_size) {
        this.is_size = is_size;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public static class ShopInfoBean {
        /**
         * uid : 200004
         */

        private String uid;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
