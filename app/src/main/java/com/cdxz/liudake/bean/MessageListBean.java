package com.cdxz.liudake.bean;

import java.io.Serializable;

public class MessageListBean implements Serializable {

    private String id;
    private String recive_uid;
    private String type;
    private String content;
    private String create_time;
    private String message_type;
    private String is_read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecive_uid() {
        return recive_uid;
    }

    public void setRecive_uid(String recive_uid) {
        this.recive_uid = recive_uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
