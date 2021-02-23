package com.cdxz.liudake.bean.Bus;

public class PopSuggestionBean {
    private String name;
    private String address;
    private double lng;
    private double lat;

    public PopSuggestionBean(String name, String address, double lng, double lat) {
        this.name = name;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
