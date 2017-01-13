package com.zyu.app.http;

import com.zyu.app.base.ApiPath;
import com.zyu.app.domain.LoginInfoResponse;
import com.zyu.app.domain.RequestResult;

import java.util.List;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Api请求的统一管理
 * Created by xtagwgj on 2017/1/7.
 */

public class ApiStore {

    public interface UserController {

        @POST(ApiPath.UserController.LOGIN)
        Observable<RequestResult<List<LoginInfoResponse>>> login(
                @Query("username") String username,
                @Query("password") String password,
                @Query("deviceType") int deviceType,
                @Query("deviceToken") String deviceToken
        );

        @POST(ApiPath.UserController.GET_SMS_CODE)
        Observable<RequestResult<List<String>>> getVerifyCode(
                @Query("phoneNumber") String phone, @Query("type") int type
        );


    }
}
