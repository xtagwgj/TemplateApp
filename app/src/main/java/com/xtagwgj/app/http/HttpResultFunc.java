package com.xtagwgj.app.http;

import com.elvishew.xlog.XLog;
import com.xtagwgj.app.model.RequestResult;

import rx.functions.Func1;


/**
 * 将带结果的数据转换为指定的数据格式
 * Created by xtagwgj on 16/9/25.
 */

public class HttpResultFunc<T> implements Func1<RequestResult<T>, T> {

    public static final String Tag = "HttpResultFunc";

    @Override
    public T call(RequestResult<T> tRequestResult) {
        XLog.e(Tag, "call: " + tRequestResult);
        if (!tRequestResult.isSuccess()) {
            throw new ApiException(tRequestResult.getError_desc());
        }

        return  tRequestResult.getObj();
    }
}
