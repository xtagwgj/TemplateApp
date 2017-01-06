package com.xtagwgj.app.http;

/**
 * 对服务器返回的错误代码或信息进行处理
 * Created by xtagwgj on 2016/12/13.
 */

 class ApiException extends RuntimeException {
    private static final int Error_400 = 400;
    private static final int Error_404 = 404;
    private static final int Error_408 = 408;
    private static final int Error_500 = 500;

    private static final int USER_NOT_EXIST = 100;
    private  static final int WRONG_PASSWORD = 101;

    ApiException(int resultCode, String detailsMessage) {
        getApiExceptionMessage(resultCode, detailsMessage);
    }

    ApiException(String resultCode, String detailsMessage) {
        this(Integer.valueOf(resultCode), detailsMessage);
    }


    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code, String detailsMessage) {
        String message;
        switch (code) {
            case Error_400:
                message = "错误请求";
                break;
            case Error_404:
                message = "服务器找不到请求的网页";
                break;
            case Error_408:
                message = "服务器等候请求时发生超时";
                break;
            case Error_500:
                message = "服务器遇到错误，无法完成请求";
                break;


            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;

            case WRONG_PASSWORD:
                message = "密码错误";
                break;


            default:
                message = detailsMessage;

        }
        return message;
    }
}
