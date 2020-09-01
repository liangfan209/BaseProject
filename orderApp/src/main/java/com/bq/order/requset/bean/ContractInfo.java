package com.bq.order.requset.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContractInfo implements Serializable {
    private String id;
    private String name;
    private String create_time;
    private String status_name;
    private List<String> url;
    private ArrayList<String> img_url;


    public String getStatus_name() {

        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public ArrayList<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(ArrayList<String> img_url) {
        this.img_url = img_url;
    }
}