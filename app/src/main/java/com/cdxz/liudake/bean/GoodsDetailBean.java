package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailBean implements Serializable {

    private String id;
    private String name;
    private String logo;
    private String gold;
    private String description;
    private String saleprice;
    private String memberprice;
    private String salescount;
    private String false_salescount;
    private String stock;
    private String status;
    private String compare_desc;
    private String compare_url;
    private String integral;
    private String orginalprice;
    private String is_pinzhi;
    private String is_youzhi;
    private String is_qitian;
    private ShopInfoBean shop_info;
    private int is_close;
    private int is_size;
    private int okrate;
    private int totalcount;
    private int friendcount;
    private String is_self;
    private int is_special;
    private String detail;
    private String parameters;
    private String sale_service;
    private int iscollection;
    private FareBean fare;
    private int cart_count;
    private String shareUrl;
    private List<?> commentList;
    private List<GalleryBean> gallery;
    private List<SizeBean> size;
    private String hit;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(String memberprice) {
        this.memberprice = memberprice;
    }

    public String getSalescount() {
        return salescount;
    }

    public void setSalescount(String salescount) {
        this.salescount = salescount;
    }

    public String getFalse_salescount() {
        return false_salescount;
    }

    public void setFalse_salescount(String false_salescount) {
        this.false_salescount = false_salescount;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompare_desc() {
        return compare_desc;
    }

    public void setCompare_desc(String compare_desc) {
        this.compare_desc = compare_desc;
    }

    public String getCompare_url() {
        return compare_url;
    }

    public void setCompare_url(String compare_url) {
        this.compare_url = compare_url;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getOrginalprice() {
        return orginalprice;
    }

    public void setOrginalprice(String orginalprice) {
        this.orginalprice = orginalprice;
    }

    public String getIs_pinzhi() {
        return is_pinzhi;
    }

    public void setIs_pinzhi(String is_pinzhi) {
        this.is_pinzhi = is_pinzhi;
    }

    public String getIs_youzhi() {
        return is_youzhi;
    }

    public void setIs_youzhi(String is_youzhi) {
        this.is_youzhi = is_youzhi;
    }

    public String getIs_qitian() {
        return is_qitian;
    }

    public void setIs_qitian(String is_qitian) {
        this.is_qitian = is_qitian;
    }

    public ShopInfoBean getShop_info() {
        return shop_info;
    }

    public void setShop_info(ShopInfoBean shop_info) {
        this.shop_info = shop_info;
    }

    public int getIs_close() {
        return is_close;
    }

    public void setIs_close(int is_close) {
        this.is_close = is_close;
    }

    public int getIs_size() {
        return is_size;
    }

    public void setIs_size(int is_size) {
        this.is_size = is_size;
    }

    public int getOkrate() {
        return okrate;
    }

    public void setOkrate(int okrate) {
        this.okrate = okrate;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getFriendcount() {
        return friendcount;
    }

    public void setFriendcount(int friendcount) {
        this.friendcount = friendcount;
    }

    public String getIs_self() {
        return is_self;
    }

    public void setIs_self(String is_self) {
        this.is_self = is_self;
    }

    public int getIs_special() {
        return is_special;
    }

    public void setIs_special(int is_special) {
        this.is_special = is_special;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getSale_service() {
        return sale_service;
    }

    public void setSale_service(String sale_service) {
        this.sale_service = sale_service;
    }

    public int getIscollection() {
        return iscollection;
    }

    public void setIscollection(int iscollection) {
        this.iscollection = iscollection;
    }

    public FareBean getFare() {
        return fare;
    }

    public void setFare(FareBean fare) {
        this.fare = fare;
    }

    public int getCart_count() {
        return cart_count;
    }

    public void setCart_count(int cart_count) {
        this.cart_count = cart_count;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public List<?> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<?> commentList) {
        this.commentList = commentList;
    }

    public List<GalleryBean> getGallery() {
        return gallery;
    }

    public void setGallery(List<GalleryBean> gallery) {
        this.gallery = gallery;
    }

    public List<SizeBean> getSize() {
        return size;
    }

    public void setSize(List<SizeBean> size) {
        this.size = size;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public static class ShopInfoBean implements Serializable {

        private String id;
        private String logo;
        private String name;
        private int is_follow;
        private String type;
        private String fans;
        private String uid;
        private String username;
        private String photo;
        private String is_baoyou;
        private String is_open_member;
        private String is_selfsend;
        private String is_noreason;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFans() {
            return fans;
        }

        public void setFans(String fans) {
            this.fans = fans;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getIs_baoyou() {
            return is_baoyou;
        }

        public void setIs_baoyou(String is_baoyou) {
            this.is_baoyou = is_baoyou;
        }

        public String getIs_open_member() {
            return is_open_member;
        }

        public void setIs_open_member(String is_open_member) {
            this.is_open_member = is_open_member;
        }

        public String getIs_selfsend() {
            return is_selfsend;
        }

        public void setIs_selfsend(String is_selfsend) {
            this.is_selfsend = is_selfsend;
        }

        public String getIs_noreason() {
            return is_noreason;
        }

        public void setIs_noreason(String is_noreason) {
            this.is_noreason = is_noreason;
        }
    }

    public static class FareBean implements Serializable {
    }

    public static class GalleryBean implements Serializable {

        private String id;
        private String smallUrl;
        private String originUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSmallUrl() {
            return smallUrl;
        }

        public void setSmallUrl(String smallUrl) {
            this.smallUrl = smallUrl;
        }

        public String getOriginUrl() {
            return originUrl;
        }

        public void setOriginUrl(String originUrl) {
            this.originUrl = originUrl;
        }
    }

    public static class SizeBean implements Serializable {

        private String id;
        private String name;
        private String selected;
        private List<ListBean> list;

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

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selected) {
            this.selected = selected;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {

            private String id;
            private String content;
            private String normal_price;
            private boolean select;

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

            public String getNormal_price() {
                return normal_price;
            }

            public void setNormal_price(String normal_price) {
                this.normal_price = normal_price;
            }

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }
        }
    }
}
