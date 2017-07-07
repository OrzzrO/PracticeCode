package com.me.practicecode.rxjava.api;

import com.me.practicecode.rxjava.entity.LoginRequest;
import com.me.practicecode.rxjava.entity.LoginResponse;
import com.me.practicecode.rxjava.entity.RegisterRequest;
import com.me.practicecode.rxjava.entity.RegisterResponse;
import com.me.practicecode.rxjava.entity.UserBaseInfoRequest;
import com.me.practicecode.rxjava.entity.UserBaseInfoResponse;
import com.me.practicecode.rxjava.entity.UserExtraInfoRequest;
import com.me.practicecode.rxjava.entity.UserExtraInfoResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by user on 2017/7/4.
 */

public interface Api {
    @GET
    Observable<LoginResponse> login(@Body LoginRequest request);

    @GET
    Observable<RegisterResponse> register(@Body RegisterRequest request);

    @GET
    Observable<UserBaseInfoResponse> getUserBaseInfo(@Body UserBaseInfoRequest request);

    @GET
    Observable<UserExtraInfoResponse> getUserExtraInfo(@Body UserExtraInfoRequest request);

    @GET("v2/movie/top250")
    Observable<Response<ResponseBody>> getTop250(@Query("start") int start, @Query("count") int count);
}
