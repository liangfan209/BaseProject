package com.bq.order.requset.bean;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/8/6
 * 版权：
 */
public class AppVersionBean {

    private AppVersionInfo edition_info;

    public AppVersionInfo getEdition_info() {
        return edition_info;
    }

    public void setEdition_info(AppVersionInfo edition_info) {
        this.edition_info = edition_info;
    }

    public static class AppVersionInfo{
        private String number;
        private String url;
        private boolean is_force_update;
        private String content;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isIs_force_update() {
            return is_force_update;
        }

        public void setIs_force_update(boolean is_force_update) {
            this.is_force_update = is_force_update;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
