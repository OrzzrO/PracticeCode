package com.me.practicecode.rxjava.api;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created by user on 2017/7/6.
 */

public interface BaiduService {

    @GET("/")
    Flowable<ResponseBody> getText();

}
