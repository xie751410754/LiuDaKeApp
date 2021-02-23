package com.cdxz.liudake.bean.Bus;

public class UpdateUserInfoBean {
    private String head;
    private String name;
    private String gender;
    private long birthday;

    public UpdateUserInfoBean(String head, String name, String gender, long birthday) {
        this.head = head;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getHead() {
        return head;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public long getBirthday() {
        return birthday;
    }
}
