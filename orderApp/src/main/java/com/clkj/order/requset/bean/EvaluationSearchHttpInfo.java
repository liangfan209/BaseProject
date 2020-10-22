package com.clkj.order.requset.bean;

import java.io.Serializable;

public class EvaluationSearchHttpInfo implements Serializable {
    private Boolean has_pics;
    private Boolean has_videos;
    private Boolean good_service;
    private Boolean course_all;
    private Boolean sale_guarantee;


    public boolean isHas_pics() {
        return has_pics;
    }

    public void setHas_pics(boolean has_pics) {
        this.has_pics = has_pics;
    }

    public boolean isHas_videos() {
        return has_videos;
    }

    public void setHas_videos(boolean has_videos) {
        this.has_videos = has_videos;
    }

    public boolean isGood_service() {
        return good_service;
    }

    public void setGood_service(boolean good_service) {
        this.good_service = good_service;
    }

    public boolean isCourse_all() {
        return course_all;
    }

    public void setCourse_all(boolean course_all) {
        this.course_all = course_all;
    }

    public boolean isSale_guarantee() {
        return sale_guarantee;
    }

    public void setSale_guarantee(boolean sale_guarantee) {
        this.sale_guarantee = sale_guarantee;
    }


}