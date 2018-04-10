package com.zpcampus.search.model;

/**
 * Created by len.zhang on 2015/12/31.
 */
public class WorkCity {
    //id
    private int citynum;
    //城市
    private String city;
    //id
    private int regionnum;
    //地区
    private String region;

    public int getCitynum() {
        return citynum;
    }

    public void setCitynum(int citynum) {
        this.citynum = citynum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRegionnum() {
        return regionnum;
    }

    public void setRegionnum(int regionnum) {
        this.regionnum = regionnum;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
