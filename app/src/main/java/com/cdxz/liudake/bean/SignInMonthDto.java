package com.cdxz.liudake.bean;

import java.util.List;

public class SignInMonthDto {

    /**
     * code : 1
     * msg :
     * count : 30
     * cons : 0
     * data : [{"mydate":"2021.6.1","jifen":"0.00","issign":"0"},{"mydate":"2021.6.2","jifen":"0.00","issign":"1"},{"mydate":"2021.6.3","jifen":"0.00","issign":"0"},{"mydate":"2021.6.4","jifen":"0.00","issign":"0"},{"mydate":"2021.6.5","jifen":"0.00","issign":"0"},{"mydate":"2021.6.6","jifen":"0.00","issign":"0"},{"mydate":"2021.6.7","jifen":"0.00","issign":"0"},{"mydate":"2021.6.8","jifen":"0.00","issign":"0"},{"mydate":"2021.6.9","jifen":"0.00","issign":"0"},{"mydate":"2021.6.10","jifen":"0.00","issign":"0"},{"mydate":"2021.6.11","jifen":"0.00","issign":"0"},{"mydate":"2021.6.12","jifen":"0.00","issign":"0"},{"mydate":"2021.6.13","jifen":"0.00","issign":"0"},{"mydate":"2021.6.14","jifen":"0.00","issign":"0"},{"mydate":"2021.6.15","jifen":"0.00","issign":"0"},{"mydate":"2021.6.16","jifen":"0.00","issign":"0"},{"mydate":"2021.6.17","jifen":"0.00","issign":"0"},{"mydate":"2021.6.18","jifen":"0.00","issign":"0"},{"mydate":"2021.6.19","jifen":"0.00","issign":"0"},{"mydate":"2021.6.20","jifen":"0.00","issign":"0"},{"mydate":"2021.6.21","jifen":"0.00","issign":"0"},{"mydate":"2021.6.22","jifen":"0.00","issign":"0"},{"mydate":"2021.6.23","jifen":"0.00","issign":"0"},{"mydate":"2021.6.24","jifen":"0.00","issign":"0"},{"mydate":"2021.6.25","jifen":"0.00","issign":"0"},{"mydate":"2021.6.26","jifen":"0.00","issign":"0"},{"mydate":"2021.6.27","jifen":"0.00","issign":"0"},{"mydate":"2021.6.28","jifen":"0.00","issign":"0"},{"mydate":"2021.6.29","jifen":"0.00","issign":"0"},{"mydate":"2021.6.30","jifen":"0.00","issign":"0"}]
     */

    private int code;
    private String msg;
    private int count;
    private int cons;
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

    public int getCons() {
        return cons;
    }

    public void setCons(int cons) {
        this.cons = cons;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * mydate : 2021.6.1
         * jifen : 0.00
         * issign : 0
         */

        private String mydate;
        private String jifen;
        private String issign;

        public String getMydate() {
            return mydate;
        }

        public void setMydate(String mydate) {
            this.mydate = mydate;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getIssign() {
            return issign;
        }

        public void setIssign(String issign) {
            this.issign = issign;
        }
    }
}
