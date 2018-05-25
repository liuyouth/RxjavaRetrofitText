package com.iolll.liubo.retrofitrxjava.model.net;


import com.iolll.liubo.retrofitrxjava.model.bean.GankResult;
import com.iolll.liubo.retrofitrxjava.model.net.exception.ExceptionEngine;
import com.iolll.liubo.retrofitrxjava.model.net.exception.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理Rx线程
 * Created by LiuBo on 2018/5/16.
 */
public class RxTransformer {


    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 针对gankio 所做的转换
     * @param <T>
     * @return
     */
    public static <T> Function<GankResult<T>, ObservableSource<? extends T>> exCloudFailure() {
        return new Function<GankResult<T>, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(GankResult<T> tGankResult) throws Exception {
                if (!tGankResult.isError())
                    return Observable.just(tGankResult.getResults());
                throw new ServerException(tGankResult.isError() ? 200 : 300,"接口错误");
            }
        };
    }



//    /**
//     * json to list
//     * 转换json 同时将非200情况转为服务器异常
//     * @param <T>
//     * @return
//     */
//    public static <T> Function<? super ResponseOuterBean,? extends ObservableSource<BaseResponseBean<T>>> gsonFrom() {
//        return new Function<ResponseOuterBean, ObservableSource<BaseResponseBean<T>>>() {
//            @Override
//            public ObservableSource<BaseResponseBean<T>> apply(@NonNull ResponseOuterBean responseOuterBean) throws Exception {
//                if (responseOuterBean.getStatus() == 200) {
//                    if (!TextUtils.isEmpty(responseOuterBean.getData())) {
//                        Type typeToken = new TypeToken<T>(){}.getType();
//                        Gson gson = new Gson();
//                        T d = gson.fromJson(responseOuterBean.getData(),typeToken);
//                        return Observable.just(new BaseResponseBean<>(responseOuterBean, d));
//                    }
//                    return Observable.error(new Exception("noData"));
//                }
//                throw new ServerException(responseOuterBean.getStatus(),responseOuterBean.getMsg());
//            }
//        };
//    }

//    /**
//     * json to Bean
//     * 转换json 同时将非200情况转为服务器异常
//     * @param beanClass
//     * @param <T>
//     * @return
//     */
//    public static <T> Function<? super ResponseOuterBean,? extends ObservableSource<BaseResponseBean<T>>> gsonFrom(final Class beanClass) {
//        return new Function<ResponseOuterBean, ObservableSource<BaseResponseBean<T>>>() {
//            @Override
//            public ObservableSource<BaseResponseBean<T>> apply(@NonNull ResponseOuterBean responseOuterBean) throws Exception {
//                if (responseOuterBean.getStatus() == 200) {
//                    if (!TextUtils.isEmpty(responseOuterBean.getData())) {
//                        Gson gson = new Gson();
//                        T d = (T) gson.fromJson(responseOuterBean.getData(),beanClass);
//                        return Observable.just(new BaseResponseBean<>(responseOuterBean, d));
//                    }
//                    return Observable.error(new Exception("noData"));
//                }
//                throw new ServerException(responseOuterBean.getStatus(),responseOuterBean.getMsg());
//            }
//        };
//    }


    public static class HttpResultFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(@NonNull Throwable throwable) throws Exception {
            return Observable.error(ExceptionEngine.handleException(throwable));
        }
    }


    /**
     * 统一线程处理 绑定生命周期
     * @param tLifecycleTransformer
     * @param <T>
     * @return
     */
//    public static <T> ObservableTransformer<T, T> transformer(LifecycleTransformer<T> tLifecycleTransformer) {
//        return new ObservableTransformer<T,T>(){
//            @Override
//            public ObservableSource<T> apply(Observable<T> upstream) {
//                return upstream.subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .compose(tLifecycleTransformer);
//            }
//        };
//    }


}

