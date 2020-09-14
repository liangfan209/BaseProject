package com.clkj.user_center.api.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/27/027
 * 版权：
 */
public class UserInfoConfigBean {

    private List<ModuleListBean> moduleList;

    public List<ModuleListBean> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleListBean> moduleList) {
        this.moduleList = moduleList;
    }

    public static class ModuleListBean {
        /**
         * name : 昵称
         * value : 安安宝
         * type : nikeName
         * hasInterval : false
         */

        private String name;
        private String value;
        private String type;
        private boolean hasInterval;
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isHasInterval() {
            return hasInterval;
        }

        public void setHasInterval(boolean hasInterval) {
            this.hasInterval = hasInterval;
        }
    }
}
