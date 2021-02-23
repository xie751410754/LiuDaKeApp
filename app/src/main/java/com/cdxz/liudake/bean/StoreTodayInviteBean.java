package com.cdxz.liudake.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 商户今日推广
 */
public class StoreTodayInviteBean extends Object implements MultiItemEntity {

    /**
     * type : 2
     * create_time : 1603789381
     * name : 李梓瑞
     * phone : 18140349329
     * sort : 1
     */

    private String type;
    private String create_time;
    private String name;
    private String phone;
    private int sort;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
