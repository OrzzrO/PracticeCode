package com.me.practicecode.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.me.practicecode.R;
import com.me.practicecode.rxjava.api.Api;
import com.me.practicecode.rxjava.api.BaiduService;
import com.me.practicecode.rxjava.entity.LoginRequest;
import com.me.practicecode.rxjava.entity.LoginResponse;
import com.me.practicecode.rxjava.entity.RegisterRequest;
import com.me.practicecode.rxjava.entity.RegisterResponse;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by user on 2017/7/3.
 */

public class RxJavaTestActivity
    extends AppCompatActivity {
    private static final String TAG = "RxJavaTestActivity";
    private Button mBtn_start;
    private Button mBtn_request;
    private Flowable<Integer> mFlowable;

    private Subscription mSubscription;
    //    static {
    //        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
    //            @Override
    //            public void accept(Throwable o)
    //                throws Exception {
    //                if ( o instanceof InterruptedIOException) {
    //                    Log.w(TAG, "IO interrupted");
    //                }
    //
    //            }
    //
    //        });
    //    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window
            .getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
         window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_rxjavatest);
        initView();
        //        initRxjava1();
        //                initRxjava2();
        //                initRxjava3();
        //                initRxjava4();
        //        initRxjava5();
        //        initRxjava6();
        //        initRxjava7();
        //                initRxjava8();
        //                    initRxjava9();
        //                    initRxjava10();
        //        initRxjava11();
        //        initRxjava12();
        //            initRxjava13();
        //        initRxjava14();
        //        initRxjava15();
        //        initRxjava16();
        //        initRxjava17();
//        initRxjava18();
     //   initRxjava19();

        initRxjava20();
    }

    /**
     * retrofit rxjava2配合使用.
     *
     */
    private void initRxjava20() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.baidu.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        BaiduService service = retrofit.create(BaiduService.class);
        service.getText()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ResponseBody>() {
                @Override
                public void onSubscribe(Subscription s) {
                    Log.w(TAG, "onSubscribe: " );
                    s.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(ResponseBody s) {
                    try {
                        Log.w(TAG, "onNext: 事件响应成功 == " + s.string() );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Throwable t) {
                    Log.w(TAG, "onError: 网络请求失败" + t );
                }

                @Override
                public void onComplete() {
                    Log.w(TAG, "onComplete: 网络访问完成" );
                }
            });


    }

    /**
     * 当flowable不是我们自己创建的时候,如何进行Backpressure策略
     */
    private void initRxjava19() {
        Flowable.interval(1,TimeUnit.MICROSECONDS)
            .onBackpressureDrop()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Long>() {
                @Override
                public void onSubscribe(Subscription s) {
                    Log.w(TAG, "onSubscribe: " );
                    s.request(Long.MAX_VALUE);
                }

                @Override
                public void onNext(Long aLong) {
                    Log.w(TAG, "onNext: "+ aLong);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Throwable t) {
                    Log.w(TAG, "onError: " + t );
                }

                @Override
                public void onComplete() {
                    Log.w(TAG, "onComplete: " );
                }
            });

    }

    private void initView() {
        mBtn_start = (Button) findViewById(R.id.btn_start);
        mBtn_request = (Button) findViewById(R.id.btn_request);
        mBtn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlowable != null) {
                    mFlowable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onSubscribe(Subscription s) {
                                Log.w(TAG, "onSubscribe: ");
                                //                    s.request(Long.MAX_VALUE);
                                mSubscription = s;
//                                s.request(128);
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.w(TAG, "onNext: " + integer);
                            }

                            @Override
                            public void onError(Throwable t) {
                                Log.w(TAG, "onError: " + t);
                            }

                            @Override
                            public void onComplete() {
                                Log.w(TAG, "onComplete: ");
                            }
                        });
                }
            }
        });
        mBtn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubscription != null) {
                    mSubscription.request(128);

                }
            }
        });


    }

    /**
     * 背压策略的测试
     * 分为 MISSING,ERROR,DROP,LATEST,BUFFER
     */
    private void initRxjava18() {
        mFlowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e)
                throws Exception {
                ////
                //                    Log.w(TAG, "emit " + 1);
                //                    e.onNext(1);
                //                    Log.w(TAG, "emit " + 2);
                //                    e.onNext(2);
                //                    Log.w(TAG, "emit " + 3);
                //                    e.onNext(3);
                //                    Log.w(TAG, "emit " + 4);
                //                    e.onNext(4);
                //                    Log.w(TAG, "emit : complete");
                //                    e.onComplete();
                for (int i = 0; i < 10000 ; i++) {
                    e.onNext(i);
                }

            }
        }, BackpressureStrategy.DROP);


    }

    /**
     * Flowable的使用
     */
    private void initRxjava17() {
        Flowable<Integer> upStream = Flowable
            .create(new FlowableOnSubscribe<Integer>() {
                @Override
                public void subscribe(FlowableEmitter<Integer> e)
                    throws Exception {
                    Log.w(TAG, "emit" + 1);
                    e.onNext(1);
                    Log.w(TAG, "emit" + 2);
                    e.onNext(2);
                    Log.w(TAG, "emit" + 3);
                    e.onNext(3);
                    Log.w(TAG, "emit" + 4);
                    e.onNext(4);
                    Log.w(TAG, "emit ::onComplete");
                    e.onComplete();
                }
            }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io());
        Subscriber<Integer> downStream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.w(TAG, "onSubscribe: ");
                //这个request().相当于告诉上游,下游处理事件的能力.
                //request(1),意思就是上游发送的事件中,下游只能处理一个事件.
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                Log.w(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: " + t);
            }

            @Override
            public void onComplete() {
                Log.w(TAG, "onComplete: ");
            }
        };
        upStream
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(downStream);

    }

    /**
     * 异步情况下,通过控制事件发射的速度来 控制 发送事件和接收事件不平衡的情况.
     */
    private void initRxjava16() {
        Observable<Integer> observable1 = Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    //无限循环发送事件
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                        Thread.sleep(2000);
                    }
                }
            })
            .subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e)
                    throws Exception {
                    e.onNext("A");
                }
            })
            .subscribeOn(Schedulers.io());
        Observable
            .zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                @Override
                public String apply(Integer integer, String s)
                    throws Exception {
                    return integer + s;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w(TAG, s);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable)
                    throws Exception {
                    Log.w(TAG, throwable);
                }
            });


    }

    /**
     * 通过sample来解决例10 OOM的问题.
     */
    private void initRxjava15() {
        Observable<Integer> observable1 = Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    //无限循环发送事件
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .sample(2, TimeUnit.SECONDS);
        Observable<String> observable2 = Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e)
                    throws Exception {
                    e.onNext("A");
                }
            })
            .subscribeOn(Schedulers.io());
        Observable
            .zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                @Override
                public String apply(Integer integer, String s)
                    throws Exception {
                    return integer + s;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w(TAG, s);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable)
                    throws Exception {
                    Log.w(TAG, throwable);
                }
            });


    }

    /**
     * 通过sample来控制事件发送的速度.每隔2秒,向下游发送事件
     */
    private void initRxjava14() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            .sample(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Log.w(TAG, "accept: " + integer);
                }
            });
    }

    /**
     * 通过filter控制上游发送事件的速度,
     * 只接收能被100整除的整数,运行可以发现,并不会导致程序OOM.
     */
    private void initRxjava13() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }
                }
            })
            .subscribeOn(Schedulers.io())
            //过滤 只接收能被100整除的事件
            .filter(new Predicate<Integer>() {
                @Override
                public boolean test(Integer integer)
                    throws Exception {
                    return integer % 100 == 0;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Log.w(TAG, "accept: " + integer);
                }
            });


    }

    /**
     * 线程异步的情况下,当上下游发送,接收事件的速度不同的情况下,仍然会oom
     */
    private void initRxjava12() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }

                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Thread.sleep(2000);
                    Log.w(TAG, "accept: " + integer);
                }
            });

    }

    /**
     * 当线程为同步的时候,这个事件是同步的,并不会导致oom.
     */
    private void initRxjava11() {
        Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    //无限循环发送事件
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }
                }
            })
            .subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer)
                    throws Exception {
                    Thread.sleep(2000);
                    Log.w(TAG, "accept: " + integer);

                }
            });
    }

    /**
     * 无限发送事件会导致OOM
     */
    private void initRxjava10() {
        Observable<Integer> observable1 = Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    //无限循环发送事件
                    for (int i = 0; ; i++) {
                        e.onNext(i);
                    }

                }
            })
            .subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e)
                    throws Exception {
                    e.onNext("A");
                }
            })
            .subscribeOn(Schedulers.io());
        Observable
            .zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                @Override
                public String apply(Integer integer, String s)
                    throws Exception {
                    return integer + s;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w(TAG, s);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable)
                    throws Exception {
                    Log.w(TAG, throwable);
                }
            });


    }


    /**
     * zip操作符, 这里使用全局static代码块来捕获异常,防止崩溃
     * zip操作符,可以将2个发送的事件组合为1个事件,然后让观察者来进行接收.
     */
    private void initRxjava9() {
        Observable<Integer> observable1 = Observable
            .create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> e)
                    throws Exception {
                    Log.w(TAG, "emmit :1 ");
                    e.onNext(1);
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit :2 ");
                    e.onNext(2);
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit :3 ");
                    e.onNext(3);
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit :4 ");
                    e.onNext(4);
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit :onComplete ");
                    e.onComplete();
                    Log.w(TAG,
                          "subscribe: Thread A ==" + Thread
                              .currentThread()
                              .getName());

                }
            })
            .subscribeOn(Schedulers.io());
        Observable<String> observable2 = Observable
            .create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e)
                    throws Exception {
                    Log.w(TAG, "emmit: A");
                    e.onNext("A");
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit: B");
                    e.onNext("B");
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit: C");
                    e.onNext("C");
                    Thread.sleep(1000);
                    Log.w(TAG, "emmit: onComplete");
                    e.onComplete();
                    Log.w(TAG,
                          "subscribe: Thread B ==" + Thread
                              .currentThread()
                              .getName());
                }
            })
            .subscribeOn(Schedulers.io());
        Observable
            .zip(observable1, observable2, new BiFunction<Integer, String, String>() {
                @Override
                public String apply(Integer integer, String s)
                    throws Exception {
                    return integer + s;
                }
            })
            .subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.w(TAG, "onSubscribe: ");
                }

                @Override
                public void onNext(String value) {
                    Log.w(TAG, "onNext: " + value);
                }

                @Override
                public void onError(Throwable e) {
                    Log.w(TAG, "onError: ");
                }

                @Override
                public void onComplete() {
                    Log.w(TAG, "onComplete: ");
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
               public void accept(RegisterResponse registerResponse)
                   throws Exception {
                   //先根据注册的响应结果去做一些操作
               }
           })
           .observeOn(Schedulers.io())                 //回到IO线程去发起登录请求
           .flatMap(new Function<RegisterResponse, ObservableSource<LoginResponse>>() {
               @Override
               public ObservableSource<LoginResponse> apply(RegisterResponse registerResponse)
                   throws Exception {
                   return api.login(new LoginRequest());
               }
           })
           .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求登录的结果
           .subscribe(new Consumer<LoginResponse>() {
               @Override
               public void accept(LoginResponse loginResponse)
                   throws Exception {
                   Toast
                       .makeText(RxJavaTestActivity.this, "登录成功", Toast.LENGTH_SHORT)
                       .show();
               }
           }, new Consumer<Throwable>() {
               @Override
               public void accept(Throwable throwable)
                   throws Exception {
                   Toast
                       .makeText(RxJavaTestActivity.this, "登录失败", Toast.LENGTH_SHORT)
                       .show();
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
                    int random = 1 + (int) (Math.random() * 10);
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
                    Log.w(TAG,
                          "subscribe: onNext thread = " + Thread
                              .currentThread()
                              .getName());
                }
            })
            .map(new Function<Integer, String>() {
                @Override
                public String apply(Integer integer)
                    throws Exception {
                    Log.w(TAG,
                          "apply: map thread = " + Thread
                              .currentThread()
                              .getName());
                    return "数据发送为 int 类型,转换为string " + integer;

                }
            })
            .doOnNext(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w(TAG,
                          "accept: doOnNext thread =" + Thread
                              .currentThread()
                              .getName());
                }
            })
            .subscribeOn(Schedulers.newThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String s)
                    throws Exception {
                    Log.w("hongTest", s);
                    Log.w(TAG,
                          "accept: observer thread = " + Thread
                              .currentThread()
                              .getName());
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
