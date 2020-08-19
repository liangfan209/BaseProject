package com.bq.order.requset.bean;

import com.blankj.utilcode.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/7/31
 * 版权：
 */
public class ProductInfo implements Serializable {
    private String id;
    private String thumbnail;
    private String title;
    private String school_name;
    private String major_name;
    private String duration;
    private String school_city;
    private String production_name;
    private String category;
    private String brand_name;
    private String despatch_type;
    private int sale_price;
    private int month_quantity;
    private int min_price;
    private String description;
    private String  video_display;
    private String agent_name;
    private List<String> detail;
    private List<String> slideshow;
    private List<SpecificationList> specification_list;

    private String imgPath;
    private String attrubute;
    private int realPrice;
    private int count;
    private int selectPosition;

    public String getCategory() {
        if(StringUtils.isEmpty(category)){
            return "高起专";
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(int realPrice) {
        this.realPrice = realPrice;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAttrubute() {
        return attrubute;
    }

    public void setAttrubute(String attrubute) {
        this.attrubute = attrubute;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public int getMonth_quantity() {
        return month_quantity;
    }

    public void setMonth_quantity(int month_quantity) {
        this.month_quantity = month_quantity;
    }

    public String getDespatch_type() {
        if(despatch_type.equals("eduction_contract")){
            return "教育合同";
        }else if(despatch_type.equals("top_up_phone")){
            return "手机充值";
        }else if(despatch_type.equals("logistics")){
            return "物流交付";
        }
        return despatch_type;
    }

    public void setDespatch_type(String despatch_type) {
        this.despatch_type = despatch_type;
    }

    public String getVideo_display() {
        return video_display;
    }

    public void setVideo_display(String video_display) {
        this.video_display = video_display;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

    public List<String> getSlideshow() {
        return slideshow;
    }

    public void setSlideshow(List<String> slideshow) {
        this.slideshow = slideshow;
    }

    public List<SpecificationList> getSpecification_list() {
        return specification_list;
    }

    public void setSpecification_list(List<SpecificationList> specification_list) {
        this.specification_list = specification_list;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSchool_city() {
        return school_city;
    }

    public void setSchool_city(String school_city) {
        this.school_city = school_city;
    }

    public String getProduction_name() {
        return production_name;
    }

    public void setProduction_name(String production_name) {
        this.production_name = production_name;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }
}
