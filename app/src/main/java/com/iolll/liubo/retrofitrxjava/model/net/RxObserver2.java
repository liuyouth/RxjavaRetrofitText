package com.iolll.liubo.retrofitrxjava.model.net;

import android.content.Context;
import android.text.TextUtils;

import com.iolll.liubo.retrofitrxjava.model.net.cancle.ApiCancleManager;
import com.iolll.liubo.retrofitrxjava.model.net.exception.ApiException;
import com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR;
import com.iolll.liubo.retrofitrxjava.utils.NetworkUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Date：2018/3/19
 * Time：17:39
 * author：CH
 */

public abstract class RxObserver2<T> implements Observer<T> {

    public Context context;
    //添加tag标签，统一管理请求(比如取消请求等操作)
    private String tag;

    public RxObserver2(Context context) {
        this.context = context;
    }




    @Override
    public void onSubscribe(@NonNull Disposable d) {

        //是否有网络
        if (NetworkUtils.isConnected()) {
            tag = ApiCancleManager.getInstance().getTagValue();
            if (!TextUtils.isEmpty(tag)) {
                ApiCancleManager.getInstance().add(tag, d);
            }
        } else {
            //没有网络的时候，调用error方法，并且切断与上游的联系
            if (!d.isDisposed()) {
                d.dispose();
            }
        }
    }

    @Override
    public void onNext(@NonNull T response) {
        //关闭弹窗进度条
        onSuccess(response);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        if(e instanceof ApiException){
            ApiException apiException = (ApiException)e;
            onErrors(apiException);
            //分发业务错误和链接错误
            switch (apiException.getCode()){
                case ERROR.UNKNOWN:
                    onFailed(apiException);
                    break;
                default:
                    onErrors(apiException);
                    break;
            }
        }else{
            onErrors(new ApiException(e, ERROR.UNINTERCEPT));
        }

    }




    /**
     * 请求成功回调
     *
     * @param response 服务端返回的数据
     */
    public abstract void onSuccess(T response);

    /**
     * 请求失败的回调 (非 200的情况)，开发者手动去触发
     *
     * @param e 数据
     */
    public void onFailed(ApiException e) {
    }

    /**
     * 连接异常时回调，手动触发
     */
    public void onErrors(@NonNull ApiException e) {
    }


}
