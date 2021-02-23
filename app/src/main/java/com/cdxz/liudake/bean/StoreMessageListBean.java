package com.cdxz.liudake.bean;

import java.util.List;

public class StoreMessageListBean {


    /**
     * count : 3
     * no_read_count : 3
     * list : [{"id":"3","recive_uid":"228631","type":"1","content":"3333","create_time":"0","message_type":"3","is_read":"0"},{"id":"2","recive_uid":"228631","type":"3","content":"2222","create_time":"0","message_type":"2","is_read":"0"}]
     */

    private String count;
    private String no_read_count;
    private List<ListBean> list;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNo_read_count() {
        return no_read_count;
    }

    public void setNo_read_count(String no_read_count) {
        this.no_read_count = no_read_count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 3
         * recive_uid : 228631
         * type : 1
         * content : 3333
         * create_time : 0
         * message_type : 3
         * is_read : 0
         */

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
}
