package com.cdxz.liudake.bean;

import java.io.Serializable;

public class UploadBean implements Serializable {

    private int status;
    private ImageBean image;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public static class ImageBean implements Serializable {

        private String urllarge;
        private String urlsmall;
        private int width;
        private int height;

        public String getUrllarge() {
            return urllarge;
        }

        public void setUrllarge(String urllarge) {
            this.urllarge = urllarge;
        }

        public String getUrlsmall() {
            return urlsmall;
        }

        public void setUrlsmall(String urlsmall) {
            this.urlsmall = urlsmall;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
