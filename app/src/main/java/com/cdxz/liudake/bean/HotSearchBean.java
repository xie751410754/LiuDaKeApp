package com.cdxz.liudake.bean;

import java.io.Serializable;

public class HotSearchBean implements Serializable {

    private String keyword;
    private String search_type;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }
}
