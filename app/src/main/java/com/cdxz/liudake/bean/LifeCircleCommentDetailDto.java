package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class LifeCircleCommentDetailDto implements Serializable {


    /**
     * id : 9666
     * logo : /Uploads/Picture/message/20210331/6063bec110949.jpg
     * open_start_time : 07:00
     * open_end_time : 20:00
     * contact : 18733033017
     * address : 沧州市新华区交通北大道(沧州市第七中学东北侧约50米)
     * lng : 116.892597
     * lat : 38.344968
     * name : 美馨日用品
     * description : 日用品零售
     * cat_id : 0
     * average_money : 10
     * shop_photos : /Uploads/Picture/message/20210326/605dc77436d84.jpeg,/Uploads/Picture/message/20210326/605dc78669293.jpeg,/Uploads/Picture/message/20210326/605dc78fc6aef.jpeg,/Uploads/Picture/message/20210326/605dc7ab19055.jpeg
     * is_convention : 0
     * actual_address : null
     * cate_name : 暂无
     * is_follow : 0
     * is_collect : 0
     * claiming : 0
     * membercount : 0
     * notice : {}
     * is_close : 0
     * shareUrl : http://127.0.0.1/index.php/Home/Share/shopDetail/id/9666/invite/229438
     * shop_star : 3.0000
     * low_star_count : 2
     * mid_star_count : 1
     * high_star_count : 2
     * have_image : 3
     * comments_count : 5
     * comments : [{"id":"1","shop_id":"9666","uid":"229437","star":"5","content":"哈哈哈哈哈哈哈哈哈","image":[],"like":"0","is_display":"1","create_time":"2021-07-05 16:15:12","name":"尊敬的溜达客用户","head":"/Public/home/images/heard.jpg"},{"id":"2","shop_id":"9666","uid":"229437","star":"3","content":"哈哈哈哈哈哈哈哈哈","image":["http://47.108.198.70/Uploads/Picture/message/20201116/5fb1ee13516ec.png"],"like":"0","is_display":"1","create_time":"2021-07-05 16:21:35","name":"尊敬的溜达客用户","head":"/Public/home/images/heard.jpg"},{"id":"3","shop_id":"9666","uid":"229437","star":"4","content":"哈哈哈哈哈哈哈哈哈","image":["http://47.108.198.70/Uploads/Picture/message/20201116/5fb1ee13516ec.png"],"like":"0","is_display":"1","create_time":"2021-07-05 16:23:57","name":"尊敬的溜达客用户","head":"/Public/home/images/heard.jpg"},{"id":"4","shop_id":"9666","uid":"229437","star":"2","content":"哈哈哈哈哈哈哈哈哈","image":[],"like":"0","is_display":"1","create_time":"2021-07-05 16:46:08","name":"尊敬的溜达客用户","head":"/Public/home/images/heard.jpg"},{"id":"5","shop_id":"9666","uid":"229437","star":"1","content":"哈哈哈哈哈哈哈哈哈","image":["/Uploads/Picture/20210705/6aef4aa79342db95.png"],"like":"0","is_display":"1","create_time":"2021-07-05 17:41:03","name":"尊敬的溜达客用户","head":"/Public/home/images/heard.jpg"}]
     */

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
    private Object actual_address;
    private String cate_name;
    private int is_follow;
    private int is_collect;
    private int claiming;
    private String membercount;
    private NoticeDTO notice;
    private int is_close;
    private String shareUrl;
    private String shop_star;
    private String low_star_count;
    private String mid_star_count;
    private String high_star_count;
    private String have_image;
    private int comments_count;
    private List<CommentsDTO> comments;

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

    public Object getActual_address() {
        return actual_address;
    }

    public void setActual_address(Object actual_address) {
        this.actual_address = actual_address;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public int getIs_collect() {
        return is_collect;
    }

    public void setIs_collect(int is_collect) {
        this.is_collect = is_collect;
    }

    public int getClaiming() {
        return claiming;
    }

    public void setClaiming(int claiming) {
        this.claiming = claiming;
    }

    public String getMembercount() {
        return membercount;
    }

    public void setMembercount(String membercount) {
        this.membercount = membercount;
    }

    public NoticeDTO getNotice() {
        return notice;
    }

    public void setNotice(NoticeDTO notice) {
        this.notice = notice;
    }

    public int getIs_close() {
        return is_close;
    }

    public void setIs_close(int is_close) {
        this.is_close = is_close;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShop_star() {
        return shop_star;
    }

    public void setShop_star(String shop_star) {
        this.shop_star = shop_star;
    }

    public String getLow_star_count() {
        return low_star_count;
    }

    public void setLow_star_count(String low_star_count) {
        this.low_star_count = low_star_count;
    }

    public String getMid_star_count() {
        return mid_star_count;
    }

    public void setMid_star_count(String mid_star_count) {
        this.mid_star_count = mid_star_count;
    }

    public String getHigh_star_count() {
        return high_star_count;
    }

    public void setHigh_star_count(String high_star_count) {
        this.high_star_count = high_star_count;
    }

    public String getHave_image() {
        return have_image;
    }

    public void setHave_image(String have_image) {
        this.have_image = have_image;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public List<CommentsDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentsDTO> comments) {
        this.comments = comments;
    }

    public static class NoticeDTO {
    }

    public static class CommentsDTO {
        /**
         * id : 1
         * shop_id : 9666
         * uid : 229437
         * star : 5
         * content : 哈哈哈哈哈哈哈哈哈
         * image : []
         * like : 0
         * is_display : 1
         * create_time : 2021-07-05 16:15:12
         * name : 尊敬的溜达客用户
         * head : /Public/home/images/heard.jpg
         */

        private String id;
        private String shop_id;
        private String uid;
        private String star;
        private String content;
        private String like;
        private String is_display;
        private String create_time;
        private String name;
        private String head;
        private List<?> image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getIs_display() {
            return is_display;
        }

        public void setIs_display(String is_display) {
            this.is_display = is_display;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public List<?> getImage() {
            return image;
        }

        public void setImage(List<?> image) {
            this.image = image;
        }
    }
}
