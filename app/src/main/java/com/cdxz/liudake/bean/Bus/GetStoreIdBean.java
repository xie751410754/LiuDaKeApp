package com.cdxz.liudake.bean.Bus;

public class GetStoreIdBean {
    private String cat_id;
    private String fastcateid;

    public GetStoreIdBean() {
    }

    public GetStoreIdBean(String cat_id, String fastcateid) {
        this.cat_id = cat_id;
        this.fastcateid = fastcateid;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getFastcateid() {
        return fastcateid;
    }
}
