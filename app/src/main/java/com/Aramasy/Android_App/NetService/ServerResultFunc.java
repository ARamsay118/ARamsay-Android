package com.Aramasy.Android_App.NetService;

import com.Aramasy.Android_App.NetService.Api.Response;
import com.Aramasy.Android_App.NetService.Exception.ServerException;

import io.reactivex.functions.Function;

/**
 * 数据返回错误时，抛出异常
 * 将数据部分剥离出来
 * @param <T>
 */
public class ServerResultFunc<T> implements Function<Response<T>,T> {
    @Override
    public T apply(Response<T> response) throws Exception {
        if(!response.getSuccess())
            throw new ServerException(Integer.parseInt(response.getErr().getCode()),response.getErr().getMsg());
        return response.getData();
    }
}
