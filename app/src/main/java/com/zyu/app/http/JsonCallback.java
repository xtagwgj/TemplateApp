package com.zyu.app.http;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.zyu.app.domain.RequestResult;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Json解析类
 * Created by xtagwgj on 2017/2/24.
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    static final String TAG = JsonCallback.class.getSimpleName();

    /**
     * 解析网络返回的数据回调
     */
    @Override
    public T convertSuccess(Response response) throws Exception {
        Log.e(TAG, "convertSuccess: " + response);

        Type type = this.getClass().getGenericSuperclass();
        T parseResponse;

        if (type instanceof ParameterizedType) {
            //如果用户写了泛型，就会进入这里，否者不会执行
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type beanType = parameterizedType.getActualTypeArguments()[0];

            //服务器未正常响应
            if (response.code() != 200) {
                RequestResult requestBase = new RequestResult();
                requestBase.setSuccess(false);
                requestBase.setError_code(response.code() + "");
                requestBase.setError_desc(response.message());

                return (T) requestBase;

            } else if (beanType == String.class) {
                //如果是String类型，直接返回字符串
                parseResponse = (T) response.body().string();

            } else {
                //如果是 Bean List Map ，则解析完后返回
                parseResponse = new Gson().fromJson(response.body().string(), beanType);

            }
        } else {
            //如果没有写泛型，直接返回Response对象
            parseResponse = (T) response;
        }

        return parseResponse;
    }

    /**
     * 网络请求成功的回调
     */
    @Override
    public void onSuccess(T object, Call call, Response response) {
        Log.e(TAG, "onSuccess: " + object);
    }

    /**
     * 解析网络失败的数据回调
     */
    @Override
    public void parseError(Call call, Exception e) {
        Log.e(TAG, "parseError: " + e.getMessage());
    }

    /**
     * 网络请求失败的回调
     */
    @Override
    public void onError(Call call, Response response, Exception e) {
        Log.e(TAG, "onError: " + e.getMessage());
    }
}
