package com.cdxz.liudake.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.List;

public class LifeCircleCatBean implements Serializable, IPickerViewData {

    private String id;
    private String name;
    private List<ChildBean> child;

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

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }

    public static class ChildBean implements Serializable,IPickerViewData {

        private String id;
        private String name;
        private List<?> ads;

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

        public List<?> getAds() {
            return ads;
        }

        public void setAds(List<?> ads) {
            this.ads = ads;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
}
