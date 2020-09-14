package com.clkj.user_center.api.bean;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/6/24
 * 版权：
 */
public class UserCenterConfigBean {
    private List<ModuleListBean> moduleList;

    public List<ModuleListBean> getModuleList() {
        return moduleList;
    }
    public void setModuleList(List<ModuleListBean> moduleList) {
        this.moduleList = moduleList;
    }
    public static class ModuleListBean {
        /**
         * name : 我的订单
         * type : horizontal
         * tabList : [{"name":"待付款","icon":"user_center_icon_order_wait_payment","path":"/order/orderManager","parmas":0,
         * "jumpType":"arouter"},{"name":"待发货","icon":"user_center_icon_order_wait_sent_out","path":"/order/orderManager","parmas":1,
         * "jumpType":"arouter"},{"name":"待收货","icon":"user_center_icon_order_wait_receive","path":"/order/orderManager","parmas":2,
         * "jumpType":"arouter"},{"name":"已完成","icon":"user_center_icon_order_complet","path":"/order/orderManager","parmas":2,
         * "jumpType":"arouter"}]
         */

        private String name;
        private String type;
        private List<TabListBean> tabList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<TabListBean> getTabList() {
            return tabList;
        }

        public void setTabList(List<TabListBean> tabList) {
            this.tabList = tabList;
        }

        public static class TabListBean {
            /**
             * name : 待付款
             * icon : user_center_icon_order_wait_payment
             * path : /order/orderManager
             * parmas : 0
             * jumpType : arouter
             */

            private String name;
            private String icon;
            private String path;
            private int parmas;
            private String jumpType;
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public int getParmas() {
                return parmas;
            }

            public void setParmas(int parmas) {
                this.parmas = parmas;
            }

            public String getJumpType() {
                return jumpType;
            }

            public void setJumpType(String jumpType) {
                this.jumpType = jumpType;
            }
        }
    }
}
