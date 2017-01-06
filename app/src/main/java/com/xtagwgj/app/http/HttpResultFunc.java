package com.xtagwgj.app.http;

import com.elvishew.xlog.XLog;
import com.xtagwgj.app.domain.RequestResult;

import rx.functions.Func1;


/**
 * 将带结果的数据转换为指定的数据格式
 * Created by xtagwgj on 16/9/25.
 */

public class HttpResultFunc<T> implements Func1<RequestResult<T>, T> {

    private static final String Tag = "HttpResultFunc";

    @Override
    public T call(RequestResult<T> tRequestResult) {
        XLog.e(Tag, "服务器返回等待处理的数据: ");

        if (!tRequestResult.isSuccess()) {
            XLog.e(Tag, "服务器返回数据携带错误信息: " + tRequestResult.getError_code() + "->" + tRequestResult.getError_desc());
            throw new ApiException(tRequestResult.getError_code(), tRequestResult.getError_desc());
        }

        return tRequestResult.getObj();
    }
}
