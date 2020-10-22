package com.clkj.order.requset.bean;

import java.io.Serializable;
import java.util.List;

public class FeedBackHttpInfo implements Serializable {

    private String type;
    private String describe;
    private List<String> img_url;

    public FeedBackHttpInfo(String type, String describe, List<String> img_url) {
        this.type = type;
        this.describe = describe;
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }
}