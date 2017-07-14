package com.me.practicecode.retrofit.api;

import com.me.practicecode.retrofit.bean.Translation;
import com.me.practicecode.retrofit.bean.YoudaoTranslation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by user on 2017/7/13.
 */

public interface Api {


    /**
     * retrofit将网络请求的url地址分成两个部分
     * retrofit 将url地址一部分放在自己定的网络请求的接口中,
     * 另一个放在实例化retrofit对象的baseUrl中
     * 如果接口中是一个完整的url请求,那么实例化的时候的baseUrl可以不写
     *
     * @return
     */
    @GET("ajax.php?a=fy&f=auto&t=zh&w=hello%20world")
    Call<Translation> getCall();


   // api?q=miracle&appKey=13b61efd3b52ea53&to=zh_CHS&sign=AC3652458E18B2ED81ADD4232A4A7261&salt=1499997192085&from=en
   @GET("api/")
    Call<YoudaoTranslation> getCall(@QueryMap(encoded = true) Map<String,String>  maps);
}
