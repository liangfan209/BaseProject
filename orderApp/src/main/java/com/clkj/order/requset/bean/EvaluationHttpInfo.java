package com.clkj.order.requset.bean;

import java.io.Serializable;

public class EvaluationHttpInfo implements Serializable {

    private String tags;
    private String content;
    private String pics;
    private String videos;
    private int server_attitude;
    private int course_quality;
    private int major;

    public EvaluationHttpInfo(String content, int server_attitude, int course_quality, int major) {
        this.content = content;
        this.server_attitude = server_attitude;
        this.course_quality = course_quality;
        this.major = major;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public int getServer_attitude() {
        return server_attitude;
    }

    public void setServer_attitude(int server_attitude) {
        this.server_attitude = server_attitude;
    }

    public int getCourse_quality() {
        return course_quality;
    }

    public void setCourse_quality(int course_quality) {
        this.course_quality = course_quality;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }
}