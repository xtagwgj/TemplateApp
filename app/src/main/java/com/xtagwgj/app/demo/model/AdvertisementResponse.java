package com.xtagwgj.app.demo.model;

/**
 * 广告
 * Created by zy on 2016/7/12.
 */
public class AdvertisementResponse  {
    private int id;
    private int orgId;
    private String pictureUrl;
    private int status;
    private String createTime;
    private String content;
    private int sort;
    private int type;
    private String url;
    private String hpCode;

    public int getId() {
        return id;
    }

    public int getOrgId() {
        return orgId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public int getStatus() {
        return status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getContent() {
        return content;
    }

    public int getSort() {
        return sort;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getHpCode() {
        return hpCode;
    }

    @Override
    public String toString() {
        return "AdvertisementResponse{" +
                "id=" + id +
                ", orgId=" + orgId +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", hpCode='" + hpCode + '\'' +
                '}';
    }
}
