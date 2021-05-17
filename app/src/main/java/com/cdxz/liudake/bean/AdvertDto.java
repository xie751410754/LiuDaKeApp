package com.cdxz.liudake.bean;

public class AdvertDto {


    /**
     * id : 3
     * multimedia_url : http://47.108.198.70/Uploads/Picture/message/20210513/609ccb2aada27.jpg
     * description : 123
     * url : null
     * type : 1
     * status : 1
     * create_time : 1620888370
     */

    private String id;
    private String multimedia_url;
    private String description;
    private Object url;
    private String type;
    private String status;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMultimedia_url() {
        return multimedia_url;
    }

    public void setMultimedia_url(String multimedia_url) {
        this.multimedia_url = multimedia_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
