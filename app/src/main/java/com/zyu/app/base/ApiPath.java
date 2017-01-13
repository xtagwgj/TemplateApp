package com.zyu.app.base;

/**
 * 地址管理
 * Created by xtagwgj on 2017/1/7.
 */

public class ApiPath {
    private static final String BASE_URL = "http://10.168.100.28:8080/propertyApp/";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * 用户操作
     */
    public class UserController {

        public static final String LOGIN = "authLogin.do";

        public static final String GET_SMS_CODE = "authSmsCode.do";

    }
}
