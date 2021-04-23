package com.cdxz.liudake.bean;

import java.util.List;

public class JDGoodsDto {

    /**
     * code : 1
     * msg :
     * count : 8
     * data : [{"GoodNum":"3219553","name":"福来恩（FRONTLINE）狗体外驱虫滴剂 中型犬宠物驱虫狗去跳蚤蜱虫药品法国进口 单支装","brandName":"福来恩（FRONTLINE）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/134492/23/5204/155924/5f193cf1Ee2557e2c/a2d7849ea14e7b1c.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"0.010","productArea":"法国土鲁兹市","Jifen":"0.00","SalePrice":"87.00","JD_Price":"87.00","Index":"8025252","category":"6994;6997;7016","SaleCount":"0","Random":"8025252"},{"GoodNum":"4022957","name":"宝路 成犬狗粮 4kg鸡肉味 中小型犬泰迪茶杯犬柯基全价粮","brandName":"宝路（Pedigree）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/122757/9/9739/160956/5f3a386aEe2f5f742/7d11b4a9918bcb4e.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"4.050","productArea":"中国北京","Jifen":"0.00","SalePrice":"129.90","JD_Price":"129.90","Index":"6836253","category":"6994;6995;7002","SaleCount":"0","Random":"6836253"},{"GoodNum":"545959","name":"伟嘉 成猫猫粮 1.3kg鸡肉味 布偶蓝猫橘猫加菲英短猫咪全价粮","brandName":"伟嘉（whiskas）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/43991/6/6863/178685/5d076505E2cc05b5b/763c2f02207c7804.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"1.340","productArea":"中国大陆","Jifen":"0.00","SalePrice":"52.90","JD_Price":"52.90","Index":"6450500","category":"6994;6995;7003","SaleCount":"0","Random":"6450500"},{"GoodNum":"100006705400","name":"伟嘉 宠物猫粮 幼猫全价粮 营养加油站系列 布偶蓝猫橘猫加菲英短猫咪 海洋鱼口味 2kg","brandName":"伟嘉（whiskas）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/75638/5/4110/130634/5d255522E3bf185a1/88828fa0adefaa1b.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"2.060","productArea":"中国大陆","Jifen":"0.00","SalePrice":"109.90","JD_Price":"109.90","Index":"4878251","category":"6994;6995;7003","SaleCount":"0","Random":"4878251"},{"GoodNum":"100001036536","name":"麦富迪 猫条 进口猫咪零食成猫幼猫营养猫湿粮金枪鱼味14g*5","brandName":"麦富迪（Myfoodie）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/57264/26/9132/349143/5d673accE6936ef67/fcbcaed3143758d4.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"3","weight":"0.080","productArea":"泰国","Jifen":"0.00","SalePrice":"18.00","JD_Price":"18.00","Index":"4645938","category":"6994;6996;7009","SaleCount":"0","Random":"4645938"},{"GoodNum":"4742638","name":"伟嘉 幼猫猫粮 2kg海洋鱼味 布偶蓝猫橘猫加菲英短猫咪全价粮","brandName":"伟嘉（whiskas）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/50631/35/2890/212150/5d0b5b14E5d8b4b7a/3e1e57b00e6236d1.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"2.050","productArea":"中国大陆","Jifen":"0.00","SalePrice":"79.90","JD_Price":"79.90","Index":"4155730","category":"6994;6995;7003","SaleCount":"0","Random":"4155730"},{"GoodNum":"100004492506","name":"尚宝海藻狗粮10kg牛肉味海藻双拼狗粮成犬通用狗粮20斤","brandName":"尚宝","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/124102/24/18081/123678/5fac06c4Ef96b5907/9135bc98fabdafc1.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"10.200","productArea":"中国大陆","Jifen":"0.00","SalePrice":"199.00","JD_Price":"199.00","Index":"3957001","category":"6994;6995;7002","SaleCount":"0","Random":"3957001"},{"GoodNum":"1501179","name":"伟嘉 宠物猫粮 成猫全价粮 布偶蓝猫橘猫加菲英短猫咪 干湿搭配套装","brandName":"伟嘉（whiskas）","imagePath":"http://img30.360buyimg.com/sku/jfs/t1/56352/23/6271/246827/5d3edce3Ebbc147a8/9603f7c3850250b4.jpg","isJDLogistics":"1","isSelf":"1","LowestBuy":"1","weight":"5.850","productArea":"中国北京","Jifen":"0.00","SalePrice":"239.90","JD_Price":"239.90","Index":"1697611","category":"6994;6995;7003","SaleCount":"0","Random":"1697611"}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * GoodNum : 3219553
         * name : 福来恩（FRONTLINE）狗体外驱虫滴剂 中型犬宠物驱虫狗去跳蚤蜱虫药品法国进口 单支装
         * brandName : 福来恩（FRONTLINE）
         * imagePath : http://img30.360buyimg.com/sku/jfs/t1/134492/23/5204/155924/5f193cf1Ee2557e2c/a2d7849ea14e7b1c.jpg
         * isJDLogistics : 1
         * isSelf : 1
         * LowestBuy : 1
         * weight : 0.010
         * productArea : 法国土鲁兹市
         * Jifen : 0.00
         * SalePrice : 87.00
         * JD_Price : 87.00
         * Index : 8025252
         * category : 6994;6997;7016
         * SaleCount : 0
         * Random : 8025252
         */

        private String GoodNum;
        private String name;
        private String brandName;
        private String imagePath;
        private String isJDLogistics;
        private String isSelf;
        private String LowestBuy;
        private String weight;
        private String productArea;
        private String Jifen;
        private String SalePrice;
        private String JD_Price;
        private String Index;
        private String category;
        private String SaleCount;
        private String Random;

        public String getGoodNum() {
            return GoodNum;
        }

        public void setGoodNum(String GoodNum) {
            this.GoodNum = GoodNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getIsJDLogistics() {
            return isJDLogistics;
        }

        public void setIsJDLogistics(String isJDLogistics) {
            this.isJDLogistics = isJDLogistics;
        }

        public String getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(String isSelf) {
            this.isSelf = isSelf;
        }

        public String getLowestBuy() {
            return LowestBuy;
        }

        public void setLowestBuy(String LowestBuy) {
            this.LowestBuy = LowestBuy;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getProductArea() {
            return productArea;
        }

        public void setProductArea(String productArea) {
            this.productArea = productArea;
        }

        public String getJifen() {
            return Jifen;
        }

        public void setJifen(String Jifen) {
            this.Jifen = Jifen;
        }

        public String getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(String SalePrice) {
            this.SalePrice = SalePrice;
        }

        public String getJD_Price() {
            return JD_Price;
        }

        public void setJD_Price(String JD_Price) {
            this.JD_Price = JD_Price;
        }

        public String getIndex() {
            return Index;
        }

        public void setIndex(String Index) {
            this.Index = Index;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSaleCount() {
            return SaleCount;
        }

        public void setSaleCount(String SaleCount) {
            this.SaleCount = SaleCount;
        }

        public String getRandom() {
            return Random;
        }

        public void setRandom(String Random) {
            this.Random = Random;
        }
    }
}
