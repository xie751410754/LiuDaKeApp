package com.cdxz.liudake.bean;


import java.util.List;

public class SignInListDto {


    /**
     * uid : 230124
     * continue : 1
     * data : [{"index":"1","week":"周一","issign":"0","jifen":"0"},{"index":"2","week":"周二","issign":"0","jifen":"0"},{"index":"3","week":"周三","issign":"0","jifen":"0"},{"index":"4","week":"今日","issign":"1","jifen":"0.00"},{"index":"5","week":"周五","issign":"0","jifen":"0"},{"index":"6","week":"周六","issign":"0","jifen":"0"},{"index":"7","week":"周日","issign":"0","jifen":"0"}]
     */

    private String uid;
    public String cons;
    private List<DataDTO> data;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContinueX() {
        return cons;
    }

    public void setContinueX(String continueX) {
        this.cons = continueX;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * index : 1
         * week : 周一
         * issign : 0
         * jifen : 0
         */

        private String index;
        private String week;
        private String issign;
        private String jifen;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getIssign() {
            return issign;
        }

        public void setIssign(String issign) {
            this.issign = issign;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }
    }
}
