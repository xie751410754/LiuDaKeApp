package com.cdxz.liudake.bean;

import java.io.Serializable;

public class LifeCircleDetailBean implements Serializable {

    private String id;
    private String logo;
    private String open_start_time;
    private String open_end_time;
    private String contact;
    private String address;
    private String lng;
    private String lat;
    private String name;
    private String description;
    private String cat_id;
    private String average_money;
    private String shop_photos;
    private String is_convention;
    private String cate_name;
    private String parent_cate_name;
    private String parent_cate_id;
    private String is_follow;
    private String is_collect;
    private String claiming;
    private String membercount;
    private NoticeBean notice;
    private String is_close;
    private String shareUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getAverage_money() {
        return average_money;
    }

    public void setAverage_money(String average_money) {
        this.average_money = average_money;
    }

    public String getShop_photos() {
        return shop_photos;
    }

    public void setShop_photos(String shop_photos) {
        this.shop_photos = shop_photos;
    }

    public String getIs_convention() {
        return is_convention;
    }

    public void setIs_convention(String is_convention) {
        this.is_convention = is_convention;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getParent_cate_name() {
        return parent_cate_name;
    }

    public void setParent_cate_name(String parent_cate_name) {
        this.parent_cate_name = parent_cate_name;
    }

    public String getParent_cate_id() {
        return parent_cate_id;
    }

    public void setParent_cate_id(String parent_cate_id) {
        this.parent_cate_id = parent_cate_id;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(String is_collect) {
        this.is_collect = is_collect;
    }

    public String getClaiming() {
        return claiming;
    }

    public void setClaiming(String claiming) {
        this.claiming = claiming;
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

    public String getIs_close() {
        return is_close;
    }

    public void setIs_close(String is_close) {
        this.is_close = is_close;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public static class NoticeBean implements Serializable {

        private String id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
