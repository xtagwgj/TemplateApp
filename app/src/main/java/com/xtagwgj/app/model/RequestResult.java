package com.xtagwgj.app.model;

/**
 * 网络请求返回对象公共抽象类
 * Created by xtagwgj on 16/9/25.
 */

public class RequestResult<T> {
    private boolean success;
    private String obj_name;
    private String error_code;
    private String error_desc;

    private T obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getObj_name() {
        return obj_name;
    }

    public void setObj_name(String obj_name) {
        this.obj_name = obj_name;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
