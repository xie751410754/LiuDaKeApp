package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class ActiveDto implements Serializable {


    /**
     * id : 1
     * title : 热卖推荐
     * subtitle : 好物推荐-副标题
     * url : /Uploads/Picture/20201029/2.png,/Uploads/Picture/20201029/3.png
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
     * goods : [{"id":"20466070314","name":"测试山王之水已","saleprice":"22.00","cx_id":"1","goods_id":"20466070314","cx_price":"1.00","cx_points":"1.00","cx_postage":"1.00","price_type":"2","number":"1","limit_user":"1","limit_times":"1","sort":"1","status":"1","create_time":"0","smallUrl":"/Uploads/Picture/20201116/s_a0c8f44c2ba96d72.jpeg","url":"https://www.liudashop.com/Uploads/Picture/20201116/s_a0c8f44c2ba96d72.jpeg"},{"id":"20446111220","name":"意可可（ecoco）卫生间拖把夹 居家日用","saleprice":"0.01","cx_id":"1","goods_id":"20446111220","cx_price":"2.00","cx_points":"2.00","cx_postage":"2.00","price_type":"2","number":"2","limit_user":"2","limit_times":"2","sort":"1","status":"1","create_time":"0","smallUrl":"/Uploads/Picture/20201031/s_d54b404c0a3aa3e2.jpg","url":"https://www.liudashop.com/Uploads/Picture/20201031/s_d54b404c0a3aa3e2.jpg"}]
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
    private List<GoodsDTO> goods;

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

    public List<GoodsDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDTO> goods) {
        this.goods = goods;
    }

    public static class GoodsDTO {
        /**
         * id : 20466070314
         * name : 测试山王之水已
         * saleprice : 22.00
         * cx_id : 1
         * goods_id : 20466070314
         * cx_price : 1.00
         * cx_points : 1.00
         * cx_postage : 1.00
         * price_type : 2
         * number : 1
         * limit_user : 1
         * limit_times : 1
         * sort : 1
         * status : 1
         * create_time : 0
         * smallUrl : /Uploads/Picture/20201116/s_a0c8f44c2ba96d72.jpeg
         * url : https://www.liudashop.com/Uploads/Picture/20201116/s_a0c8f44c2ba96d72.jpeg
         */

        private String id;
        private String name;
        private String saleprice;
        private String cx_id;
        private String goods_id;
        private String cx_price;
        private String cx_points;
        private String cx_postage;
        private String price_type;
        private String number;
        private String limit_user;
        private String limit_times;
        private String sort;
        private String status;
        private String create_time;
        private String smallUrl;
        private String url;

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

        public String getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(String saleprice) {
            this.saleprice = saleprice;
        }

        public String getCx_id() {
            return cx_id;
        }

        public void setCx_id(String cx_id) {
            this.cx_id = cx_id;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getCx_price() {
            return cx_price;
        }

        public void setCx_price(String cx_price) {
            this.cx_price = cx_price;
        }

        public String getCx_points() {
            return cx_points;
        }

        public void setCx_points(String cx_points) {
            this.cx_points = cx_points;
        }

        public String getCx_postage() {
            return cx_postage;
        }

        public void setCx_postage(String cx_postage) {
            this.cx_postage = cx_postage;
        }

        public String getPrice_type() {
            return price_type;
        }

        public void setPrice_type(String price_type) {
            this.price_type = price_type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getLimit_user() {
            return limit_user;
        }

        public void setLimit_user(String limit_user) {
            this.limit_user = limit_user;
        }

        public String getLimit_times() {
            return limit_times;
        }

        public void setLimit_times(String limit_times) {
            this.limit_times = limit_times;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getSmallUrl() {
            return smallUrl;
        }

        public void setSmallUrl(String smallUrl) {
            this.smallUrl = smallUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
