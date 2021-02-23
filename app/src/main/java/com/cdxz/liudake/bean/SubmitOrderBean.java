package com.cdxz.liudake.bean;

import java.io.Serializable;

public class SubmitOrderBean implements Serializable {

//    private UserBean user;
    private OrderBean order;
    private float payprice;
//
//    public UserBean getUser() {
//        return user;
//    }
//
//    public void setUser(UserBean user) {
//        this.user = user;
//    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public float getPayprice() {
        return payprice;
    }

    public void setPayprice(float payprice) {
        this.payprice = payprice;
    }

    public static class UserBean {

        private String gold;
        private String balance;
        private String isopen;

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getIsopen() {
            return isopen;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }
    }

    public static class OrderBean {

        private String id;
        private float totalprice;
        private float gold;
        private float goldprice;
        private float balanceprice;
        private float memeberprice;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public float getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(float totalprice) {
            this.totalprice = totalprice;
        }

        public float getGold() {
            return gold;
        }

        public void setGold(float gold) {
            this.gold = gold;
        }

        public float getGoldprice() {
            return goldprice;
        }

        public void setGoldprice(float goldprice) {
            this.goldprice = goldprice;
        }

        public float getBalanceprice() {
            return balanceprice;
        }

        public void setBalanceprice(float balanceprice) {
            this.balanceprice = balanceprice;
        }

        public float getMemeberprice() {
            return memeberprice;
        }

        public void setMemeberprice(float memeberprice) {
            this.memeberprice = memeberprice;
        }
    }
}
