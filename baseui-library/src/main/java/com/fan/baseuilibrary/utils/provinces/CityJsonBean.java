package com.fan.baseuilibrary.utils.provinces;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/27
 * 版权：
 */
public class CityJsonBean implements IPickerViewData {
    private List<City> children;
    private String label;
    private String value;

    public List<City> getChildren() {
        return children;
    }

    public void setChildren(List<City> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getPickerViewText() {
        return this.label;
    }

    public static class City implements IPickerViewData {
        private List<Area> children;
        private String label;
        private String value;

        public List<Area> getChildren() {
            return children;
        }

        public void setChildren(List<Area> children) {
            this.children = children;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String getPickerViewText() {
            return this.label;
        }
    }
    public static class Area implements IPickerViewData {
        private String label;
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String getPickerViewText() {
            return this.label;
        }
    }



}
