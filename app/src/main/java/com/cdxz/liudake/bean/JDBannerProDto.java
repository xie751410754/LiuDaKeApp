package com.cdxz.liudake.bean;

import java.util.List;

public class JDBannerProDto {

    /**
     * code : 1
     * msg :
     * count : 2
     * data : [{"ID":"4","Title":"11","Img":"http://liudake.cn/imgUploads/2021041912041141110022.jpg","Desc":"11","Url":"#","Type":"1","Index":"1","Other":"","CreateTime":"2021/4/19 12:04:18","Creator":"0"},{"ID":"3","Title":"2","Img":"http://liudake.cn/imgUploads/2021041912043243243720.jpg","Desc":"22","Url":"#","Type":"1","Index":"2","Other":"","CreateTime":"2021/4/19 12:03:52","Creator":"0"}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * ID : 4
         * Title : 11
         * Img : http://liudake.cn/imgUploads/2021041912041141110022.jpg
         * Desc : 11
         * Url : #
         * Type : 1
         * Index : 1
         * Other :
         * CreateTime : 2021/4/19 12:04:18
         * Creator : 0
         */

        private String ID;
        private String Title;
        private String Img;
        private String Desc;
        private String Url;
        private String Type;
        private String Index;
        private String Other;
        private String CreateTime;
        private String Creator;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getImg() {
            return Img;
        }

        public void setImg(String Img) {
            this.Img = Img;
        }

        public String getDesc() {
            return Desc;
        }

        public void setDesc(String Desc) {
            this.Desc = Desc;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getIndex() {
            return Index;
        }

        public void setIndex(String Index) {
            this.Index = Index;
        }

        public String getOther() {
            return Other;
        }

        public void setOther(String Other) {
            this.Other = Other;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCreator() {
            return Creator;
        }

        public void setCreator(String Creator) {
            this.Creator = Creator;
        }
    }
}
