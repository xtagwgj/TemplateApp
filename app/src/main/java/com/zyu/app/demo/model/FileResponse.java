package com.zyu.app.demo.model;

/**
 * Created by zy on 2016/7/15.
 */
public class FileResponse  {
    private String pictureName;
    private String savePath;

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "pictureName='" + pictureName + '\'' +
                ", savePath='" + savePath + '\'' +
                '}';
    }
}
