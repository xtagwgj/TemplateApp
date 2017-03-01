package com.zyu.app.base;

import com.zyu.app.BuildConfig;

/**
 * 地址管理
 * Created by xtagwgj on 2017/1/7.
 */

public class ApiPath {
    private static String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    public static String callUrl(String url) {
        return getBaseUrl() + url;
    }

    /**
     * 用户操作
     */
    public class UserController {

        public static final String LOGIN = "login";

        public static final String GET_SMS_CODE = "authSmsCode.do";

    }
}
