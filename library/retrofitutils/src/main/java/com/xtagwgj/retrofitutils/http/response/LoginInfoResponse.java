package com.xtagwgj.retrofitutils.http.response;

import com.google.gson.annotations.SerializedName;

/**
 * 登陆结果的响应
 * Created by xtagwgj on 16/9/25.
 */

public class LoginInfoResponse {
    private int userId;
    @SerializedName("username")
    private String userName;
    private String deviceType;//登录设备0为Android，1为iOS设备
    private String nickName;
    @SerializedName("headimgUrl")
    private String headImgUrl;
    private String gender;//性别0是男 1是女
    private String realname;//真实姓名
    private String hobby;//爱好
    private String mobilePhone;//手机号码
    private String email;//邮件
    private String birthday;//生日

    //推送信息的设置
    private boolean showShopInfo = true;//推送商家信息
    private boolean showSysInfo = true;//推送系统信息
    private boolean showPropertyInfo = true;//推送物业信息

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "LoginInfoResponse{" +
                "showPropertyInfo=" + showPropertyInfo +
                ", showSysInfo=" + showSysInfo +
                ", showShopInfo=" + showShopInfo +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", hobby='" + hobby + '\'' +
                ", realname='" + realname + '\'' +
                ", gender='" + gender + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", nickName='" + nickName + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
