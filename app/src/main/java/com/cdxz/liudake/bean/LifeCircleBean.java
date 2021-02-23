package com.cdxz.liudake.bean;

import java.io.Serializable;

public class LifeCircleBean implements Serializable {

    private String id;
    private String uid;
    private String logo;
    private String name;
    private String is_open_member;
    private float distance;
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
    private String average_money;

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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
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

    public String getAverage_money() {
        return average_money;
    }

    public void setAverage_money(String average_money) {
        this.average_money = average_money;
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
