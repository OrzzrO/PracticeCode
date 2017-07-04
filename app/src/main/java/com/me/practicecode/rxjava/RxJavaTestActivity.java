package com.me.practicecode.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.me.practicecode.R;
import com.me.practicecode.rxjava.entity.LoginRequest;
import com.me.practicecode.rxjava.entity.LoginResponse;
import com.me.practicecode.rxjava.entity.RegisterRequest;
import com.me.practicecode.rxjava.entity.RegisterResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by user on 2017/7/3.
 */

public class RxJavaTestActivity
    extends AppCompatActivity {
    private static final String TAG = "RxJavaTestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window
            .getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_test);
        //        initRxjava1();
        //        initRxjava2();
        //        initRxjava3();
        //        initRxjava4();
        //initRxjava5();
        //initRxjava6();
//        initRxjava7();
//        initRxjava8();
            initRxjava9();

    }

    private void initRxjava9() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e)
                throws Exception {
                Log.w(TAG, "emmit :1 " );
                e.onNext(1);
                Log.w(TAG, "emmit :2 " );
                e.onNext(2);
                Log.w(TAG, "emmit :3 " );
                e.onNext(3);
                Log.w(TAG, "emmit :4 " );
                e.onNext(4);
                Log.w(TAG, "emmit :onComplete " );
                e.onComplete();

            }
        });

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e)
                throws Exception {
                Log.w(TAG, "emmit: A" );
                e.onNext("A");
                Log.w(TAG, "emmit: B" );
                e.onNext("B");
                Log.w(TAG, "emmit: C" );
                e.onNext("C");
                Log.w(TAG, "emmit: onComplete" );
                e.onComplete();

            }
        });

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s)
                throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w(TAG, "onSubscribe: " );
            }

            @Override
            public void onNext(String value) {
                Log.w(TAG, "onNext: " + value );
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: " );
            }

            @Override
            public void onComplete() {
                Log.w(TAG, "onComplete: " );
            }
        });




    }

    /**
     * flat
     */
    private void initRxjava8() {
        final Api api = RetrofitProvider
            .get()
            .create(Api.class);

        api.register(new RegisterRequest())            //发起注册请求
           .subscribeOn(Schedulers.io())               //在IO线程进行网络请求
           .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求注册结果
           .doOnNext(new Consumer<RegisterResponse>() {
               @Override
               public void accept(RegisterResponse registerResponse) throws Exception {
                   //先根据注册的响应结果去做一些操作
               }
           })
           .observeOn(Schedulers.io())                 //回到IO线程去发起登录请求
           .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
               @Override
               public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse) throws Exception {
                   return api.login(new LoginRequest());
               }
           })
           .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求登录的结果
           .subscribe(new Consumer<LoginResponse>() {
               @Override
               public void accept(LoginResponse loginResponse) throws Exception {
                   Toast
                       .makeText(RxJavaTestActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
               }
           }, new Consumer<Throwable>() {
               @Override
               public void accept(Throwable throwable) throws Exception {
                   Toast.makeText(RxJavaTestActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
               }
           });
    }

    /**
     * flatmap的使用
     */
    private void initRxjava7() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                }
            })
            .flatMap(new Function<Integer, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(Integer integer)
                    throws Exception {
                    List<String> list = new ArrayList<String>();
                    int random=1+(int)(Math.random()*10);

                    for (int i = 0; i < 3; i++) {
                        list.add("事件被分成3个事件" + integer);
                    }
                    return Observable
                        .fromIterable(list)
                        .delay(random, TimeUnit.MILLISECONDS);
                }
            })
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w(TAG, "accept: " + s);
                }
            });


    }

    /**
     * map操作符可以将被观察者发送的数据转换为任意类型的让观察者接收.
     */
    private void initRxjava6() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    e.onNext(1);
                    e.onNext(2);
                    e.onNext(3);
                }
            })
            .map(new Function<Integer, String>() {
                @Override
                public String apply(Integer integer)
                    throws Exception {
                    return "数据发送为 int 类型,转换为string " + integer;
                }
            })
            .doOnNext(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                }
            })
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w("hongTest", s);
                }
            });


    }

    Disposable mDispoasble;

    private void initRxjava5() {
        Api api = RetrofitProvider
            .get()
            .create(Api.class);
        api
            .getTop250(0, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Response<ResponseBody>>() {
                @Override
                public void accept(Response<ResponseBody> responseBodyResponse)
                    throws Exception {
                    Log.w("hongTest",
                          "accept: " + responseBodyResponse
                              .body()
                              .toString());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable)
                    throws Exception {
                    Log.w("hongTest", "error : " + throwable.getMessage());
                }
            });
    }

    /**
     * 使用subscribeOn 指定 被观察者发送事件的线程,多次调用的情况下,仅第一次调用有效.其余的忽略 使用observerOn 指定 观察者接收事件的线程,多次调用的情况下,每调用一次,切换一次线程.
     */
    private void initRxjava4() {
        Log.w("hongTest", "initRxjava4: ");
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e)
                throws Exception {
                //                Log.w("hongTest",
                //                      "observable 发送事件的线程为为 ===" + Thread.currentThread()
                //                                                         .getName());
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        //使用参数consumer的subscribe()的重载方法
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer)
                throws Exception {
                //                Log.w("hongTest",
                //                      "观察者收到事件的线程为=== " + Thread.currentThread()
                //                                                .getName());
                //                Log.w("hongTest", "accept: next ==  " + integer);
            }
        };
        observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Log.w("hongTest",
                          "accept: 第一次接收事件的线程为" + Thread
                              .currentThread()
                              .getName());
                }
            })
            .observeOn(Schedulers.io())
            .doOnNext(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Log.w("hongTest",
                          "accept: 第二次接收事件的线程为" + Thread
                              .currentThread()
                              .getName());
                }
            })
            .subscribe(consumer);

    }

    /**
     * subscribe()的重载方法 建立联系,只接收next的事件
     */
    private void initRxjava3() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e)
                throws Exception {
                Log.w("hongTest",
                      "subscribe:  当前线程为 ===" + Thread
                          .currentThread()
                          .getName());
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        //使用参数consumer的subscribe()的重载方法
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer)
                throws Exception {
                Log.w("hongTest",
                      "观察者收到事件的线程为=== " + Thread
                          .currentThread()
                          .getName());
                Log.w("hongTest", "accept: next ==  " + integer);
            }
        };
        observable.subscribe(consumer);
    }

    /**
     * 链式调用的写法
     */
    private void initRxjava2() {
        Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e)
                    throws Exception {
                    e.onNext("越过山丘");
                    e.onNext("虽然已白了头");
                    e.onNext("喋喋不休");
                    e.onNext("再也换不回温柔");
                    e.onComplete();

                }
            })
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.w("hongTest", "onSubscribe: invoke~");
                }

                @Override
                public void onNext(String value) {
                    Log.w("hongTest", "onNext: value = " + value);
                }

                @Override
                public void onError(Throwable e) {
                    Log.w("hongTest", "onError: e = " + e.getMessage());

                }

                @Override
                public void onComplete() {
                    Log.w("hongTest", "onComplete:  invoke~~");

                }
            });

    }

    /**
     * 初识rxJava2,分开的写法. observable : 被观察者 observer : 观察者 subscribe : 订阅,将观察者和被观察者建立关系
     */
    private void initRxjava1() {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e)
                throws Exception {
                e.onNext("越过山丘");
                e.onNext("虽然已白了头");
                e.onNext("喋喋不休");
                e.onNext("再也换不回温柔");
                e.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.w("hongTest", "onSubscribe: invoke~");
            }

            @Override
            public void onNext(String value) {
                Log.w("hongTest", "onNext: value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.w("hongTest", "onError: e = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.w("hongTest", "onComplete:  invoke~~");
            }
        };
        //订阅事件.(建立关系)
        observable.subscribe(observer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDispoasble != null) {
            mDispoasble.dispose();
        }
    }
}
