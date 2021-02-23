package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class LoginBean implements Serializable {
    private String uid;
    private String bind_phone;
    private int bind_qq;
    private int bind_wechat;
    private String card_no;
    private String gold;
    private String balance;
    private String head;
    private String headlarge;
    private String name;
    private String gender;
    private String birthday;
    private String birthday_time;
    private String sign;
    private String love;
    private String profession;
    private String company;
    private String school;
    private String createtime;
    private String openfire_pass;
    private int isfollow;
    private int is_logistics;
    private String lasttime;
    private int is_check;
    private String share_balance;
    private String fansCount;
    private DynamicsBean dynamics;
    private String xizuetoken;
    private List<ShopBean> shop_list;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBind_phone() {
        return bind_phone;
    }

    public void setBind_phone(String bind_phone) {
        this.bind_phone = bind_phone;
    }

    public int getBind_qq() {
        return bind_qq;
    }

    public void setBind_qq(int bind_qq) {
        this.bind_qq = bind_qq;
    }

    public int getBind_wechat() {
        return bind_wechat;
    }

    public void setBind_wechat(int bind_wechat) {
        this.bind_wechat = bind_wechat;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeadlarge() {
        return headlarge;
    }

    public void setHeadlarge(String headlarge) {
        this.headlarge = headlarge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday_time() {
        return birthday_time;
    }

    public void setBirthday_time(String birthday_time) {
        this.birthday_time = birthday_time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLove() {
        return love;
    }

    public void setLove(String love) {
        this.love = love;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getOpenfire_pass() {
        return openfire_pass;
    }

    public void setOpenfire_pass(String openfire_pass) {
        this.openfire_pass = openfire_pass;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public void setIsfollow(int isfollow) {
        this.isfollow = isfollow;
    }

    public int getIs_logistics() {
        return is_logistics;
    }

    public void setIs_logistics(int is_logistics) {
        this.is_logistics = is_logistics;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public String getShare_balance() {
        return share_balance;
    }

    public void setShare_balance(String share_balance) {
        this.share_balance = share_balance;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public DynamicsBean getDynamics() {
        return dynamics;
    }

    public void setDynamics(DynamicsBean dynamics) {
        this.dynamics = dynamics;
    }

    public String getXizuetoken() {
        return xizuetoken;
    }

    public void setXizuetoken(String xizuetoken) {
        this.xizuetoken = xizuetoken;
    }

    public List<ShopBean> getShopList() {
        return shop_list;
    }

    public void setShopList(List<ShopBean> shopList) {
        this.shop_list = shopList;
    }

    public static class DynamicsBean implements Serializable {
    }

    public static class ShopBean implements Serializable {

        private String id;
        private String type;
        private String name;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
