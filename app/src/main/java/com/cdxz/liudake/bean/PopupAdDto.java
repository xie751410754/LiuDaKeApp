package com.cdxz.liudake.bean;

public class PopupAdDto {

    /**
     * id : 137
     * img : /Uploads/Picture/message/20210719/s_60f5513ba52d3.jpg
     * type : 7
     * url : www.thinkphp.cn
     * goodsid : null
     * htmlcontent :
     * contenttype : 1
     */

    private String id;
    private String img;
    private String type;
    private String url;
    private Object goodsid;
    private String htmlcontent;
    private String contenttype;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Object goodsid) {
        this.goodsid = goodsid;
    }

    public String getHtmlcontent() {
        return htmlcontent;
    }

    public void setHtmlcontent(String htmlcontent) {
        this.htmlcontent = htmlcontent;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }
}
