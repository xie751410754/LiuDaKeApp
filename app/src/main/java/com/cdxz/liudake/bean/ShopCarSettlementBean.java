package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车结算
 */
public class ShopCarSettlementBean implements Serializable {

    private AddressBean address;
    private UserBean user;
    private float farefee;
    private float totalprice;
    private float de_gold;
    private float memeberprice;
    private float balanceprice;
    private float price;
    private int de_gold_isshow;
    private int balanceprice_isshow;
    private String gold;
    private ServiceBean service;
    private List<ListBeanX> list;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public float getFarefee() {
        return farefee;
    }

    public void setFarefee(float farefee) {
        this.farefee = farefee;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    public float getDe_gold() {
        return de_gold;
    }

    public void setDe_gold(float de_gold) {
        this.de_gold = de_gold;
    }

    public float getMemeberprice() {
        return memeberprice;
    }

    public void setMemeberprice(float memeberprice) {
        this.memeberprice = memeberprice;
    }

    public float getBalanceprice() {
        return balanceprice;
    }

    public void setBalanceprice(float balanceprice) {
        this.balanceprice = balanceprice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDe_gold_isshow() {
        return de_gold_isshow;
    }

    public void setDe_gold_isshow(int de_gold_isshow) {
        this.de_gold_isshow = de_gold_isshow;
    }

    public int getBalanceprice_isshow() {
        return balanceprice_isshow;
    }

    public void setBalanceprice_isshow(int balanceprice_isshow) {
        this.balanceprice_isshow = balanceprice_isshow;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public List<ListBeanX> getList() {
        return list;
    }

    public void setList(List<ListBeanX> list) {
        this.list = list;
    }

    public static class AddressBean implements Serializable {

        private String id;
        private String selected;
        private String uid;
        private String name;
        private String phone;
        private String province;
        private String city;
        private String district;
        private String lat;
        private String lng;
        private String detail;
        private String createtime;
        private String map_detail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getMap_detail() {
            return map_detail;
        }

        public void setMap_detail(String map_detail) {
            this.map_detail = map_detail;
        }
    }

    public static class UserBean implements Serializable {

        private String balance;
        private String gold;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }
    }

    public static class ServiceBean implements Serializable {
    }

    public static class ListBeanX implements Serializable {

        private String id;
        private String name;
        private String shop_logo;
        private String is_hdfk;
        private String shoptype;
        private String open_status;
        private String is_selfsend;
        private String totalcount;
        private String member_balance;
        private String welfare_gold;
        private String welfare_price;
        private String welfare_integral;
        private List<ListBean> list;
        private List<FareListBean> farelist;

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

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getIs_hdfk() {
            return is_hdfk;
        }

        public void setIs_hdfk(String is_hdfk) {
            this.is_hdfk = is_hdfk;
        }

        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }

        public String getOpen_status() {
            return open_status;
        }

        public void setOpen_status(String open_status) {
            this.open_status = open_status;
        }

        public String getIs_selfsend() {
            return is_selfsend;
        }

        public void setIs_selfsend(String is_selfsend) {
            this.is_selfsend = is_selfsend;
        }

        public String getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(String totalcount) {
            this.totalcount = totalcount;
        }

        public String getMember_balance() {
            return member_balance;
        }

        public void setMember_balance(String member_balance) {
            this.member_balance = member_balance;
        }

        public String getWelfare_gold() {
            return welfare_gold;
        }

        public void setWelfare_gold(String welfare_gold) {
            this.welfare_gold = welfare_gold;
        }

        public String getWelfare_price() {
            return welfare_price;
        }

        public void setWelfare_price(String welfare_price) {
            this.welfare_price = welfare_price;
        }

        public String getWelfare_integral() {
            return welfare_integral;
        }

        public void setWelfare_integral(String welfare_integral) {
            this.welfare_integral = welfare_integral;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<FareListBean> getFarelist() {
            return farelist;
        }

        public void setFarelist(List<FareListBean> farelist) {
            this.farelist = farelist;
        }

        public static class ListBean implements Serializable {

            private String goods_id;
            private String name;
            private String logo;
            private String buycount;
            private String price;
            private String gold;
            private String extend;
            private String extend_id;
            private String goods_attr;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
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

            public String getBuycount() {
                return buycount;
            }

            public void setBuycount(String buycount) {
                this.buycount = buycount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGold() {
                return gold;
            }

            public void setGold(String gold) {
                this.gold = gold;
            }

            public String getExtend() {
                return extend;
            }

            public void setExtend(String extend) {
                this.extend = extend;
            }

            public String getExtend_id() {
                return extend_id;
            }

            public void setExtend_id(String extend_id) {
                this.extend_id = extend_id;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }
        }

        public static class FareListBean implements Serializable {

            private String id;
            private String name;
            private String alias;
            private String fee;
            private String shop_pay;
            private String selected;

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

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }

            public String getShop_pay() {
                return shop_pay;
            }

            public void setShop_pay(String shop_pay) {
                this.shop_pay = shop_pay;
            }

            public String getSelected() {
                return selected;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }
        }
    }
}
