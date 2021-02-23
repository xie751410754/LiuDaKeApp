package com.cdxz.liudake.bean.Bus;

public class ScanQRCodeBean {
    private int type;
    private String result;

    public ScanQRCodeBean(int type, String result) {
        this.type = type;
        this.result = result;
    }

    public int getType() {
        return type;
    }

    public String getResult() {
        return result;
    }
}
