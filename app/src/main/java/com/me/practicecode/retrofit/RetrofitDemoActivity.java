package com.me.practicecode.retrofit;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.practicecode.BaseActivity;
import com.me.practicecode.R;
import com.me.practicecode.realm.util.ToastUtil;
import com.me.practicecode.retrofit.api.Api;
import com.me.practicecode.retrofit.bean.Translation;
import com.me.practicecode.retrofit.bean.YoudaoTranslation;
import com.me.practicecode.retrofit.util.YoudaoApiUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 2017/7/13.
 */

public class RetrofitDemoActivity extends BaseActivity{

    private Button mQuery;
    private EditText mEdt_word;
    private TextView mTv_result;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void init() {
        super.init();


        initView();
    //    initRetrofit();
//        initRetrofit1();
        //YoudaoApiUtil.generateUrl("miracle","yN6zPLSEg81NDRTCxXpeRnI3sACYpDpC");
    }

    private void initView() {
        mQuery = (Button) findViewById(R.id.btn_query);
        mEdt_word = (EditText) findViewById(R.id.edt_word);
        mTv_result = (TextView) findViewById(R.id.tv_result);
        mQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mEdt_word
                    .getText()
                    .toString()
                    .trim();

                if (TextUtils.isEmpty(word)){
                    ToastUtil.show(RetrofitDemoActivity.this,"单词别为空");
                    return;
                }
                initRetrofit1(word);


            }
        });
    }


    private void initRetrofit1(String word) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://openapi.youdao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        Api api = retrofit.create(Api.class);
        //利用有道api获得有效的url
        Map<String, String> maps = YoudaoApiUtil.generateUrl(word);
        Call<YoudaoTranslation> call = api.getCall(maps);
        call.enqueue(new Callback<YoudaoTranslation>() {
            @Override
            public void onResponse(Call<YoudaoTranslation> call, Response<YoudaoTranslation> response) {
                Log.w("hongTest", "onResponse:  发送请求成功~" );
//                response.body().show();
                    mTv_result.setText(response.body().getTranslation().get(0));
            }

            @Override
            public void onFailure(Call<YoudaoTranslation> call, Throwable t) {
                Log.w("hongTeast", "onFailure:  发送请求失败~" );
            }
        });
    }

    private void initRetrofit() {

        //创建retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://fy.iciba.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        //创建网络请求接口的实例
        Api api = retrofit.create(Api.class);

        //对发送请求进行封装
        Call<Translation> call = api.getCall();

        ///(异步)发送请求
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Log.w("hongTest", "onResponse:  发送请求成功~" );
                response.body().show();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.w("hongTeast", "onFailure:  发送请求失败~" );
            }
        });

    }
}
