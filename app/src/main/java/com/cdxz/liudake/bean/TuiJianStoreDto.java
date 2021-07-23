package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.List;

public class TuiJianStoreDto implements Serializable {


    /**
     * id : 21272062350
     * name : 多功能不锈钢剪刀1
     * description : 可以剪,可以开瓶
     * salescount : 0
     * false_salescount : 0
     * updatetime : 1626081723
     * brand :
     * weight : 0
     * detail : ["/Uploads/Picture/20210707/9962e60fc817b8c5.png","/Uploads/Picture/20210707/d36e0ffebf86202a.jpg"]
     * parameters : 一把
     * goodscateid : 10320
     * sale_service :
     * link :
     * intoprice :
     * contactphone :
     * createtime : 1626081723
     * stock : 55
     * saleprice : 9.90
     * gold : 0
     * shopid : 9027
     * status : 1
     * integral : 0.00
     * imploadid : null
     * memberprice : 0.00
     * goods_image : []
     * is_nopic : 0
     * orginalprice : 30.00
     * hit : 0
     * goods_no : 123456789
     * item_unit :
     * packet_list :
     * pingfen : 5
     * supplier : 爱德华
     * mark : null
     */

    private String id;
    private String name;
    private String description;
    private String salescount;
    private String false_salescount;
    private String updatetime;
    private String brand;
    private String weight;
    private String parameters;
    private String goodscateid;
    private String cate_name;

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    private String sale_service;
    private String link;
    private String intoprice;
    private String contactphone;
    private String createtime;
    private String stock;
    private String saleprice;
    private String gold;
    private String shopid;
    private String status;
    private String integral;
    private Object imploadid;
    private String memberprice;
    private String is_nopic;
    private String orginalprice;
    private String hit;
    private String goods_no;
    private String item_unit;
    private String packet_list;
    private String pingfen;
    private String supplier;
    private Object mark;
    private List<String> detail;
    private List<String> goods_image;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getGoodscateid() {
        return goodscateid;
    }

    public void setGoodscateid(String goodscateid) {
        this.goodscateid = goodscateid;
    }

    public String getSale_service() {
        return sale_service;
    }

    public void setSale_service(String sale_service) {
        this.sale_service = sale_service;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIntoprice() {
        return intoprice;
    }

    public void setIntoprice(String intoprice) {
        this.intoprice = intoprice;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(String saleprice) {
        this.saleprice = saleprice;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public Object getImploadid() {
        return imploadid;
    }

    public void setImploadid(Object imploadid) {
        this.imploadid = imploadid;
    }

    public String getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(String memberprice) {
        this.memberprice = memberprice;
    }

    public String getIs_nopic() {
        return is_nopic;
    }

    public void setIs_nopic(String is_nopic) {
        this.is_nopic = is_nopic;
    }

    public String getOrginalprice() {
        return orginalprice;
    }

    public void setOrginalprice(String orginalprice) {
        this.orginalprice = orginalprice;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public String getPacket_list() {
        return packet_list;
    }

    public void setPacket_list(String packet_list) {
        this.packet_list = packet_list;
    }

    public String getPingfen() {
        return pingfen;
    }

    public void setPingfen(String pingfen) {
        this.pingfen = pingfen;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Object getMark() {
        return mark;
    }

    public void setMark(Object mark) {
        this.mark = mark;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

    public List<String> getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(List<String> goods_image) {
        this.goods_image = goods_image;
    }
}
