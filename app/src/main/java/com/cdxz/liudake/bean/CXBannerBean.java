package com.cdxz.liudake.bean;

import java.io.Serializable;

public class CXBannerBean implements Serializable {

    /**
     * id : 1
     * title : 热卖推荐
     * subtitle : 好物推荐-副标题
     * url : https://www.liudashop.com/Uploads/Picture/20201029/2.png,/Uploads/Picture/20201029/3.png
     * keyword : 衣服
     * type : 1
     * sort : 0
     * goods_id : 20446111918,100483,100481,100480,15483045640
     * start_time : 0
     * end_time : null
     * limit_all : 0
     * limit_time : 0
     * status : 1
     * create_time : 0000-00-00 00:00:00
     */

    private String id;
    private String title;
    private String subtitle;
    private String url;
    private String keyword;
    private String type;
    private String sort;
    private String goods_id;
    private String start_time;
    private Object end_time;
    private String limit_all;
    private String limit_time;
    private String status;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
    }

    public String getLimit_all() {
        return limit_all;
    }

    public void setLimit_all(String limit_all) {
        this.limit_all = limit_all;
    }

    public String getLimit_time() {
        return limit_time;
    }

    public void setLimit_time(String limit_time) {
        this.limit_time = limit_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
