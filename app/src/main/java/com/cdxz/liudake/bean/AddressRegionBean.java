package com.cdxz.liudake.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressRegionBean implements Serializable {

//       "id": "100000",
//               "name": "中国",
//               "pid": "0",

    public AddressRegionBean(){


    }


    public AddressRegionBean(String id,String name,String pid){

        this.id= id;
        this.name = name;
        this.pid = pid;
    }

    private String id;
    private String name;
    private String pid;

    private ArrayList<AddressRegionBean> sub;


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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public ArrayList<AddressRegionBean> getSub() {
        return sub;
    }

    public void setSub(ArrayList<AddressRegionBean> sub) {
        this.sub = sub;
    }

    @Override
    public String toString() {
        return "AddressRegionBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", sub=" + sub +
                '}';
    }
}
