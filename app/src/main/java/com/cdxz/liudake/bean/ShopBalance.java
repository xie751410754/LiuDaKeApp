package com.cdxz.liudake.bean;

import java.util.List;

public class ShopBalance {

    /**
     * userInfo : {"uid":"228631","sort":"","phone":"15397762902","password":"c33367701511b4f6020ec61ded352059","name":"","head":"/Public/home/images/heard.jpg","gender":"","birthday":"0","sign":"","love":"","profession":"","company":"","school":"","hometown":"","books":"","website":"","movie":"","music":"","hobby":"","iscomplete":"0","gold":"0","createtime":"1603742124","extend":"","balance":"4000.000","isopen":"0","invitecode":"200000","fee_id":"1","phone_sys":"10","phone_type":"SEA-AL10","integral":"0","openfire_pass":"654321","openid_qq":"","openid_wechat":"","lasttime":"1603742132","qrcode":"","openid_wechat_pay":null,"openid_ali_pay":null,"iscomment":"0","share_balance":"0.00","is_use":"1","revice_gold":null,"wechat_name":"","my_invitecode":""}
     * my_balance : {"tuiguang_balance":"0","integral_balance":"0","withdraw_balance":"-1000","xiaofei_balance":"-1000"}
     * list : [{"id":"6","in_out":"2","type":"6","amount":"-1000","create_time":"0","remark":"会员提现","uid":"228631","shop_id":null,"order_id":null}]
     */

    private UserInfoBean userInfo;
    private MyBalanceBean my_balance;
    private List<ListBean> list;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public MyBalanceBean getMy_balance() {
        return my_balance;
    }

    public void setMy_balance(MyBalanceBean my_balance) {
        this.my_balance = my_balance;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class UserInfoBean {
        /**
         * uid : 228631
         * sort :
         * phone : 15397762902
         * password : c33367701511b4f6020ec61ded352059
         * name :
         * head : /Public/home/images/heard.jpg
         * gender :
         * birthday : 0
         * sign :
         * love :
         * profession :
         * company :
         * school :
         * hometown :
         * books :
         * website :
         * movie :
         * music :
         * hobby :
         * iscomplete : 0
         * gold : 0
         * createtime : 1603742124
         * extend :
         * balance : 4000.000
         * isopen : 0
         * invitecode : 200000
         * fee_id : 1
         * phone_sys : 10
         * phone_type : SEA-AL10
         * integral : 0
         * openfire_pass : 654321
         * openid_qq :
         * openid_wechat :
         * lasttime : 1603742132
         * qrcode :
         * openid_wechat_pay : null
         * openid_ali_pay : null
         * iscomment : 0
         * share_balance : 0.00
         * is_use : 1
         * revice_gold : null
         * wechat_name :
         * my_invitecode :
         */

        private String uid;
        private String sort;
        private String phone;
        private String password;
        private String name;
        private String head;
        private String gender;
        private String birthday;
        private String sign;
        private String love;
        private String profession;
        private String company;
        private String school;
        private String hometown;
        private String books;
        private String website;
        private String movie;
        private String music;
        private String hobby;
        private String iscomplete;
        private String gold;
        private String createtime;
        private String extend;
        private String balance;
        private String isopen;
        private String invitecode;
        private String fee_id;
        private String phone_sys;
        private String phone_type;
        private String integral;
        private String openfire_pass;
        private String openid_qq;
        private String openid_wechat;
        private String lasttime;
        private String qrcode;
        private Object openid_wechat_pay;
        private Object openid_ali_pay;
        private String iscomment;
        private String share_balance;
        private String is_use;
        private Object revice_gold;
        private String wechat_name;
        private String my_invitecode;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
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

        public String getHometown() {
            return hometown;
        }

        public void setHometown(String hometown) {
            this.hometown = hometown;
        }

        public String getBooks() {
            return books;
        }

        public void setBooks(String books) {
            this.books = books;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getMovie() {
            return movie;
        }

        public void setMovie(String movie) {
            this.movie = movie;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public String getIscomplete() {
            return iscomplete;
        }

        public void setIscomplete(String iscomplete) {
            this.iscomplete = iscomplete;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
            this.extend = extend;
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

        public String getInvitecode() {
            return invitecode;
        }

        public void setInvitecode(String invitecode) {
            this.invitecode = invitecode;
        }

        public String getFee_id() {
            return fee_id;
        }

        public void setFee_id(String fee_id) {
            this.fee_id = fee_id;
        }

        public String getPhone_sys() {
            return phone_sys;
        }

        public void setPhone_sys(String phone_sys) {
            this.phone_sys = phone_sys;
        }

        public String getPhone_type() {
            return phone_type;
        }

        public void setPhone_type(String phone_type) {
            this.phone_type = phone_type;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getOpenfire_pass() {
            return openfire_pass;
        }

        public void setOpenfire_pass(String openfire_pass) {
            this.openfire_pass = openfire_pass;
        }

        public String getOpenid_qq() {
            return openid_qq;
        }

        public void setOpenid_qq(String openid_qq) {
            this.openid_qq = openid_qq;
        }

        public String getOpenid_wechat() {
            return openid_wechat;
        }

        public void setOpenid_wechat(String openid_wechat) {
            this.openid_wechat = openid_wechat;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public Object getOpenid_wechat_pay() {
            return openid_wechat_pay;
        }

        public void setOpenid_wechat_pay(Object openid_wechat_pay) {
            this.openid_wechat_pay = openid_wechat_pay;
        }

        public Object getOpenid_ali_pay() {
            return openid_ali_pay;
        }

        public void setOpenid_ali_pay(Object openid_ali_pay) {
            this.openid_ali_pay = openid_ali_pay;
        }

        public String getIscomment() {
            return iscomment;
        }

        public void setIscomment(String iscomment) {
            this.iscomment = iscomment;
        }

        public String getShare_balance() {
            return share_balance;
        }

        public void setShare_balance(String share_balance) {
            this.share_balance = share_balance;
        }

        public String getIs_use() {
            return is_use;
        }

        public void setIs_use(String is_use) {
            this.is_use = is_use;
        }

        public Object getRevice_gold() {
            return revice_gold;
        }

        public void setRevice_gold(Object revice_gold) {
            this.revice_gold = revice_gold;
        }

        public String getWechat_name() {
            return wechat_name;
        }

        public void setWechat_name(String wechat_name) {
            this.wechat_name = wechat_name;
        }

        public String getMy_invitecode() {
            return my_invitecode;
        }

        public void setMy_invitecode(String my_invitecode) {
            this.my_invitecode = my_invitecode;
        }
    }

    public static class MyBalanceBean {

        /**
         * shop_balance : 0.00
         * today_receive : 10
         * today_ordernums : 10
         */

        private Object shop_balance;
        private Object today_receive;
        private Object today_ordernums;

        public Object getShop_balance() {
            return shop_balance;
        }

        public void setShop_balance(Object shop_balance) {
            this.shop_balance = shop_balance;
        }

        public Object getToday_receive() {
            return today_receive;
        }

        public void setToday_receive(Object today_receive) {
            this.today_receive = today_receive;
        }

        public Object getToday_ordernums() {
            return today_ordernums;
        }

        public void setToday_ordernums(Object today_ordernums) {
            this.today_ordernums = today_ordernums;
        }
    }

    public static class ListBean {
        /**
         * id : 6
         * in_out : 2
         * type : 6
         * amount : -1000
         * create_time : 0
         * remark : 会员提现
         * uid : 228631
         * shop_id : null
         * order_id : null
         */

        private String id;
        private String in_out;
        private String type;
        private String amount;
        private String create_time;
        private String remark;
        private String uid;
        private Object shop_id;
        private Object order_id;

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

        public Object getShop_id() {
            return shop_id;
        }

        public void setShop_id(Object shop_id) {
            this.shop_id = shop_id;
        }

        public Object getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Object order_id) {
            this.order_id = order_id;
        }
    }
}
