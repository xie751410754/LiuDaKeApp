package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class OrderDetailBean {

    private String id;
    private String parentid;
    private String uid;
    private String payment;
    private String invoice;
    private String shopid;
    private float totalprice;
    private String gold;
    private float fareprice;
    private String farename;
    private AddressBean address;
    private String ispay;
    private String status;
    private String createtime;
    private String sendtime;
    private String sendnumber;
    private String paytime;
    private String receipttime;
    private String isworked;
    private String message;
    private String promotion;
    private String de_gold;
    private String balanceprice;
    private String is_gold;
    private String is_balance;
    private String goldprice;
    private String service_id;
    private String suname;
    private String suphone;
    private String service_time;
    private String user_expressfee;
    private String fh_remark;
    private String memeberprice;
    private String shop_fare;
    private String fan_gold;
    private String welfare_gold;
    private String welfare_price;
    private String welfare_integral;
    private String to_account_time;
    private String commission;
    private String isQROrder;
    private String QROrderStatus;
    private String old_totalprice;
    private String old_fareprice;
    private String receipt_amount;
    private String QROrderFee;
    private String zkprice;
    private String is_mobile_web;
    private String payprice;
    private String isreturn;
    private String shopname;
    private String shoplogo;
    private String search_express_url;
    private String fanxian;
    private String songhuotime;
    private String songda;
    private LogisticsBean logistics;
    private ShopBean shop;
    private List<OrderListBean.ListBean> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public float getFareprice() {
        return fareprice;
    }

    public void setFareprice(float fareprice) {
        this.fareprice = fareprice;
    }

    public String getFarename() {
        return farename;
    }

    public void setFarename(String farename) {
        this.farename = farename;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getSendnumber() {
        return sendnumber;
    }

    public void setSendnumber(String sendnumber) {
        this.sendnumber = sendnumber;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getReceipttime() {
        return receipttime;
    }

    public void setReceipttime(String receipttime) {
        this.receipttime = receipttime;
    }

    public String getIsworked() {
        return isworked;
    }

    public void setIsworked(String isworked) {
        this.isworked = isworked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getDe_gold() {
        return de_gold;
    }

    public void setDe_gold(String de_gold) {
        this.de_gold = de_gold;
    }

    public String getBalanceprice() {
        return balanceprice;
    }

    public void setBalanceprice(String balanceprice) {
        this.balanceprice = balanceprice;
    }

    public String getIs_gold() {
        return is_gold;
    }

    public void setIs_gold(String is_gold) {
        this.is_gold = is_gold;
    }

    public String getIs_balance() {
        return is_balance;
    }

    public void setIs_balance(String is_balance) {
        this.is_balance = is_balance;
    }

    public String getGoldprice() {
        return goldprice;
    }

    public void setGoldprice(String goldprice) {
        this.goldprice = goldprice;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getSuname() {
        return suname;
    }

    public void setSuname(String suname) {
        this.suname = suname;
    }

    public String getSuphone() {
        return suphone;
    }

    public void setSuphone(String suphone) {
        this.suphone = suphone;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getUser_expressfee() {
        return user_expressfee;
    }

    public void setUser_expressfee(String user_expressfee) {
        this.user_expressfee = user_expressfee;
    }

    public String getFh_remark() {
        return fh_remark;
    }

    public void setFh_remark(String fh_remark) {
        this.fh_remark = fh_remark;
    }

    public String getMemeberprice() {
        return memeberprice;
    }

    public void setMemeberprice(String memeberprice) {
        this.memeberprice = memeberprice;
    }

    public String getShop_fare() {
        return shop_fare;
    }

    public void setShop_fare(String shop_fare) {
        this.shop_fare = shop_fare;
    }

    public String getFan_gold() {
        return fan_gold;
    }

    public void setFan_gold(String fan_gold) {
        this.fan_gold = fan_gold;
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

    public String getTo_account_time() {
        return to_account_time;
    }

    public void setTo_account_time(String to_account_time) {
        this.to_account_time = to_account_time;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getIsQROrder() {
        return isQROrder;
    }

    public void setIsQROrder(String isQROrder) {
        this.isQROrder = isQROrder;
    }

    public String getQROrderStatus() {
        return QROrderStatus;
    }

    public void setQROrderStatus(String QROrderStatus) {
        this.QROrderStatus = QROrderStatus;
    }

    public String getOld_totalprice() {
        return old_totalprice;
    }

    public void setOld_totalprice(String old_totalprice) {
        this.old_totalprice = old_totalprice;
    }

    public String getOld_fareprice() {
        return old_fareprice;
    }

    public void setOld_fareprice(String old_fareprice) {
        this.old_fareprice = old_fareprice;
    }

    public String getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public String getQROrderFee() {
        return QROrderFee;
    }

    public void setQROrderFee(String QROrderFee) {
        this.QROrderFee = QROrderFee;
    }

    public String getZkprice() {
        return zkprice;
    }

    public void setZkprice(String zkprice) {
        this.zkprice = zkprice;
    }

    public String getIs_mobile_web() {
        return is_mobile_web;
    }

    public void setIs_mobile_web(String is_mobile_web) {
        this.is_mobile_web = is_mobile_web;
    }

    public String getPayprice() {
        return payprice;
    }

    public void setPayprice(String payprice) {
        this.payprice = payprice;
    }

    public String getIsreturn() {
        return isreturn;
    }

    public void setIsreturn(String isreturn) {
        this.isreturn = isreturn;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShoplogo() {
        return shoplogo;
    }

    public void setShoplogo(String shoplogo) {
        this.shoplogo = shoplogo;
    }

    public String getSearch_express_url() {
        return search_express_url;
    }

    public void setSearch_express_url(String search_express_url) {
        this.search_express_url = search_express_url;
    }

    public String getFanxian() {
        return fanxian;
    }

    public void setFanxian(String fanxian) {
        this.fanxian = fanxian;
    }

    public String getSonghuotime() {
        return songhuotime;
    }

    public void setSonghuotime(String songhuotime) {
        this.songhuotime = songhuotime;
    }

    public String getSongda() {
        return songda;
    }

    public void setSongda(String songda) {
        this.songda = songda;
    }

    public LogisticsBean getLogistics() {
        return logistics;
    }

    public void setLogistics(LogisticsBean logistics) {
        this.logistics = logistics;
    }

    public ShopBean getShop() {
        return shop;
    }

    public void setShop(ShopBean shop) {
        this.shop = shop;
    }

    public List<OrderListBean.ListBean> getList() {
        return list;
    }

    public void setList(List<OrderListBean.ListBean> list) {
        this.list = list;
    }

    public static class AddressBean {

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


    public static class LogisticsDetailsBean{

        private String context;
        private String ftime;
        private String time;

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class LogisticsBean {

        private String message;
        private String nu;
        private String ischeck;
        private String condition;
        private String com;
        private String status;
        private String state;
        private List<LogisticsDetailsBean> data;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNu() {
            return nu;
        }

        public void setNu(String nu) {
            this.nu = nu;
        }

        public String getIscheck() {
            return ischeck;
        }

        public void setIscheck(String ischeck) {
            this.ischeck = ischeck;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getCom() {
            return com;
        }

        public void setCom(String com) {
            this.com = com;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<LogisticsDetailsBean> getData() {
            return data;
        }

        public void setData(List<LogisticsDetailsBean> data) {
            this.data = data;
        }
    }

    public static class ShopBean {

        private String id;
        private String uid;
        private String logo;
        private String name;
        private String is_open_member;
        private String distance;
        private String lng;
        private String lat;
        private String address;
        private String contact;
        private String is_close;
        private String is_member;
        private String is_noreason;
        private String is_selfsend;
        private String is_baoyou;
        private String open_start_time;
        private String open_end_time;
        private String open_status;
        private String farename;
        private String sendnumber;
        private String membercount;
        private NoticeBean notice;
        private String is_follow;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIs_open_member() {
            return is_open_member;
        }

        public void setIs_open_member(String is_open_member) {
            this.is_open_member = is_open_member;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getIs_close() {
            return is_close;
        }

        public void setIs_close(String is_close) {
            this.is_close = is_close;
        }

        public String getIs_member() {
            return is_member;
        }

        public void setIs_member(String is_member) {
            this.is_member = is_member;
        }

        public String getIs_noreason() {
            return is_noreason;
        }

        public void setIs_noreason(String is_noreason) {
            this.is_noreason = is_noreason;
        }

        public String getIs_selfsend() {
            return is_selfsend;
        }

        public void setIs_selfsend(String is_selfsend) {
            this.is_selfsend = is_selfsend;
        }

        public String getIs_baoyou() {
            return is_baoyou;
        }

        public void setIs_baoyou(String is_baoyou) {
            this.is_baoyou = is_baoyou;
        }

        public String getOpen_start_time() {
            return open_start_time;
        }

        public void setOpen_start_time(String open_start_time) {
            this.open_start_time = open_start_time;
        }

        public String getOpen_end_time() {
            return open_end_time;
        }

        public void setOpen_end_time(String open_end_time) {
            this.open_end_time = open_end_time;
        }

        public String getOpen_status() {
            return open_status;
        }

        public void setOpen_status(String open_status) {
            this.open_status = open_status;
        }

        public String getFarename() {
            return farename;
        }

        public void setFarename(String farename) {
            this.farename = farename;
        }

        public String getSendnumber() {
            return sendnumber;
        }

        public void setSendnumber(String sendnumber) {
            this.sendnumber = sendnumber;
        }

        public String getMembercount() {
            return membercount;
        }

        public void setMembercount(String membercount) {
            this.membercount = membercount;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public static class NoticeBean {
        }
    }
}
