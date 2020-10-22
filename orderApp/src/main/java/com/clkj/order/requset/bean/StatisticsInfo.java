package com.clkj.order.requset.bean;

import java.io.Serializable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class StatisticsInfo implements Serializable {
    private int pic_numbers;
    private int video_numbers;
    private int sale_guarantee_numbers;
    private int good_service_numbers;
    private int course_all_numbers;

    public int getPic_numbers() {
        return pic_numbers;
    }

    public void setPic_numbers(int pic_numbers) {
        this.pic_numbers = pic_numbers;
    }

    public int getVideo_numbers() {
        return video_numbers;
    }

    public void setVideo_numbers(int video_numbers) {
        this.video_numbers = video_numbers;
    }

    public int getSale_guarantee_numbers() {
        return sale_guarantee_numbers;
    }

    public void setSale_guarantee_numbers(int sale_guarantee_numbers) {
        this.sale_guarantee_numbers = sale_guarantee_numbers;
    }

    public int getGood_service_numbers() {
        return good_service_numbers;
    }

    public void setGood_service_numbers(int good_service_numbers) {
        this.good_service_numbers = good_service_numbers;
    }

    public int getCourse_all_numbers() {
        return course_all_numbers;
    }

    public void setCourse_all_numbers(int course_all_numbers) {
        this.course_all_numbers = course_all_numbers;
    }
}
