package com.zyu.app.http.rx;

import com.elvishew.xlog.XLog;
import com.xtagwgj.common.commonutils.StringUtils;
import com.zyu.app.domain.RequestResult;

import rx.functions.Func1;


/**
 * 将带结果的数据转换为指定的数据格式
 * Created by xtagwgj on 16/9/25.
 */

public class HttpResultFunc<T> implements Func1<RequestResult<T>, T> {

    @Override
    public T call(RequestResult<T> tRequestResult) {
        XLog.d(String.format("服务器返回信息: %s " ,tRequestResult));

        if (!tRequestResult.isSuccess()) {

            if (StringUtils.isEmpty(tRequestResult.getError_code()))
                throw new ApiException(tRequestResult.getError_desc());
            else
                throw new ApiException(tRequestResult.getError_code(), tRequestResult.getError_desc());

        }

        return tRequestResult.getObj();
    }

}
