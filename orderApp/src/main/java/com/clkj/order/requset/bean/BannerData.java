package com.clkj.order.requset.bean;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class BannerData {

    /**
     * desc :
     * id : 20
     * imagePath : https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png
     * isVisible : 1
     * order : 2
     * title : flutter 中文社区
     * type : 1
     * url : https://flutter.cn/
     */

    public static final int TYPE_NEW = 10000;

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;
    private int drawable = -1;
    private String name;


    public BannerData(int id,String name,String thumbnail,String url, int type) {
        this.imagePath = thumbnail;
        this.type = type;
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public BannerData(String imagePath, int type) {
        this.imagePath = imagePath;
        this.type = type;
    }



    public BannerData(int imageRes, int type) {
        this.drawable = imageRes;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
