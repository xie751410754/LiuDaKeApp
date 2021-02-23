package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class HomeIndexBean implements Serializable {

    private Advertisement2Bean advertisement2;
    private List<BannerBean> imgs;
    private List<GoodsActivityClassBean> goods_activity_class;
    private List<GoodsCuxiaoBean> goods_cuxiao;
    private List<GoodsCuxiao1Bean> goods_cuxiao1;
    private List<GoodsCuxiao2Bean> goods_cuxiao2;
    private List<GoodsCuxiao3Bean> goods_cuxiao3;
    private List<GoodsCuxiao4Bean> goods_cuxiao4;
    private TimeBean time;

    public Advertisement2Bean getAdvertisement2() {
        return advertisement2;
    }

    public void setAdvertisement2(Advertisement2Bean advertisement2) {
        this.advertisement2 = advertisement2;
    }

    public List<BannerBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<BannerBean> imgs) {
        this.imgs = imgs;
    }

    public List<GoodsActivityClassBean> getGoods_activity_class() {
        return goods_activity_class;
    }

    public void setGoods_activity_class(List<GoodsActivityClassBean> goods_activity_class) {
        this.goods_activity_class = goods_activity_class;
    }

    public List<GoodsCuxiaoBean> getGoods_cuxiao() {
        return goods_cuxiao;
    }

    public void setGoods_cuxiao(List<GoodsCuxiaoBean> goods_cuxiao) {
        this.goods_cuxiao = goods_cuxiao;
    }

    public List<GoodsCuxiao1Bean> getGoods_cuxiao1() {
        return goods_cuxiao1;
    }

    public void setGoods_cuxiao1(List<GoodsCuxiao1Bean> goods_cuxiao1) {
        this.goods_cuxiao1 = goods_cuxiao1;
    }

    public List<GoodsCuxiao2Bean> getGoods_cuxiao2() {
        return goods_cuxiao2;
    }

    public void setGoods_cuxiao2(List<GoodsCuxiao2Bean> goods_cuxiao2) {
        this.goods_cuxiao2 = goods_cuxiao2;
    }

    public List<GoodsCuxiao3Bean> getGoods_cuxiao3() {
        return goods_cuxiao3;
    }

    public void setGoods_cuxiao3(List<GoodsCuxiao3Bean> goods_cuxiao3) {
        this.goods_cuxiao3 = goods_cuxiao3;
    }

    public List<GoodsCuxiao4Bean> getGoods_cuxiao4() {
        return goods_cuxiao4;
    }

    public void setGoods_cuxiao4(List<GoodsCuxiao4Bean> goods_cuxiao4) {
        this.goods_cuxiao4 = goods_cuxiao4;
    }

    public static class Advertisement2Bean implements Serializable {

        private String id;
        private String img;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class GoodsActivityClassBean implements Serializable {
        /**
         * id : 1
         * url : /Uploads/Picture/20201025/s_ae7fe04700d50653.png
         * name : 家具
         * sort : 1
         * create_time : 2011-11-11 22:02:00
         */

        private String id;
        private String url;
        private String name;
        private String sort;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class GoodsCuxiaoBean implements Serializable {
        /**
         * id : 1
         * title : 热卖推荐
         * subtitle : 好物推荐
         * url : /Uploads/Picture/20201025/s_ae7fe04700d50653.png,/Uploads/Picture/20201025/s_ae7fe04700d50653.png
         * keyword : 衣服
         * type : 1
         * sort : 0
         * goods_id : 0
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class GoodsCuxiao1Bean implements Serializable {
        /**
         * id : 1
         * title : 热卖推荐
         * subtitle : 好物推荐
         * url : /Uploads/Picture/20201025/s_ae7fe04700d50653.png,/Uploads/Picture/20201025/s_ae7fe04700d50653.png
         * keyword : 衣服
         * type : 1
         * sort : 0
         * goods_id : 0
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class GoodsCuxiao2Bean implements Serializable {
        /**
         * id : 5
         * title : 活动1
         * subtitle :
         * url : /Uploads/Picture/20201025/s_ae7fe04700d50653.png
         * keyword : 衣服
         * type : 2
         * sort : 0
         * goods_id : 0
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class GoodsCuxiao3Bean implements Serializable {
        /**
         * id : 7
         * title : 100%海外原装正品：所有商品均属海外生产
         * subtitle : 低价促销
         * url : /Uploads/Picture/20201025/s_ae7fe04700d50653.png
         * keyword : 衣服
         * type : 3
         * sort : 0
         * goods_id : 0
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class GoodsCuxiao4Bean implements Serializable {
        /**
         * id : 17501043720562544274078
         * name : 圆领色纱撞色条纹保暖内衣套装均码修身美体衣可爱款秋衣秋裤
         * gold : 12
         * saleprice : 60.00
         * salescount : 49
         * smallUrl : /Uploads/Picture/20201026/s_acf52a310cee155b.png
         */

        private String id;
        private String name;
        private String gold;
        private String saleprice;
        private String orginalprice;
        private String salescount;
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

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(String saleprice) {
            this.saleprice = saleprice;
        }

        public String getOrginalprice() {
            return orginalprice;
        }

        public void setOrginalprice(String orginalprice) {
            this.orginalprice = orginalprice;
        }

        public String getSalescount() {
            return salescount;
        }

        public void setSalescount(String salescount) {
            this.salescount = salescount;
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

    public TimeBean getTime() {
        return time;
    }

    public void setTime(TimeBean time) {
        this.time = time;
    }

    public static class TimeBean implements Serializable {

        private String id;
        private String title;
        private String createtime;
        private String start_time;
        private String end_time;
        private String status;
        private String updatetime;
        private List<ListBean> list;

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

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {

            private String id;
            private String time_id;
            private String goods_id;
            private String time_price;
            private String time_number;
            private String jia_number;
            private String name;
            private float gold;
            private String saleprice;
            private String smallUrl;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime_id() {
                return time_id;
            }

            public void setTime_id(String time_id) {
                this.time_id = time_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getTime_price() {
                return time_price;
            }

            public void setTime_price(String time_price) {
                this.time_price = time_price;
            }

            public String getTime_number() {
                return time_number;
            }

            public void setTime_number(String time_number) {
                this.time_number = time_number;
            }

            public String getJia_number() {
                return jia_number;
            }

            public void setJia_number(String jia_number) {
                this.jia_number = jia_number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getGold() {
                return gold;
            }

            public void setGold(float gold) {
                this.gold = gold;
            }

            public String getSaleprice() {
                return saleprice;
            }

            public void setSaleprice(String saleprice) {
                this.saleprice = saleprice;
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
}
