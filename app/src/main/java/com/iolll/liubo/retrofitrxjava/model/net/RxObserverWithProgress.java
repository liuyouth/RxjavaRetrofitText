package com.iolll.liubo.retrofitrxjava.model.net;

import android.content.Context;

import com.iolll.liubo.retrofitrxjava.model.net.exception.ApiException;
import com.iolll.liubo.retrofitrxjava.views.CustomProgressDialog;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * Date：2018/3/19
 * Time：17:39
 * author：CH
 */

public abstract class RxObserverWithProgress<T> extends RxObserver2<T> {


    //添加tag标签，统一管理请求(比如取消请求等操作)
    private String tag;
    private CustomProgressDialog progressDialog;

    public RxObserverWithProgress(Context context, boolean canCancle) {
        super(context);
        //弹窗进度条
        createProgressDialog(context, canCancle, "");
    }

    public RxObserverWithProgress(Context context, boolean canCancle, boolean isShowProgress) {
        super(context);
        //弹窗进度条
        if (isShowProgress) {
            createProgressDialog(context, canCancle, "");
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        super.onSubscribe(d);
        //显示进度条
        showProgressDialog();

    }

    @Override
    public void onNext(@NonNull T response) {
        super.onNext(response);
        //关闭弹窗进度条
        stopProgressDialog();

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        super.onError(e);
        //关闭弹窗进度条
        stopProgressDialog();
    }

    /**
     * 创建进度条实例
     */
    public void createProgressDialog(Context cxt, boolean canCancle, String desc) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createDialog(cxt, canCancle, desc);
                progressDialog.setCanceledOnTouchOutside(false);
                //progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动加载进度条
     */
    public void showProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭加载进度条
     */
    public void stopProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param response 数据
     */
    public void onFailed(T response) {
    }

    /**
     * 连接异常时回调，手动触发
     */
    public void onErrors(@NonNull ApiException e) {

    }

//    /**
//     * 对于异常情况的统一处理
//     *
//     * @param type 异常的类型
//     */
//    public void solveException(ExceptionType type) {
//        switch (type) {
//            case BAD_NETWORK:
//                DialogUtil.errorDialog(context, context.getString(R.string.error_msg));
//                break;
//            case PARSE_DATA_ERROR:
//                DialogUtil.errorDialog(context, context.getString(R.string.error_msg));
//                break;
//            case UNFOUND_ERROR:
//                DialogUtil.errorDialog(context, context.getString(R.string.error_msg));
//                break;
//            case TIMEOUT_ERROR:
//                DialogUtil.errorDialog(context, context.getString(R.string.error_msg));
//                break;
//            case UNKNOWN_ERROR:
//                DialogUtil.errorDialog(context, context.getString(R.string.error_msg));
//                break;
//        }
//    }

    public enum ExceptionType {
        /**
         * 无网络
         */
        BAD_NETWORK,
        /**
         * 数据解析异常
         */
        PARSE_DATA_ERROR,
        /**
         * 找不到相关连接
         */
        UNFOUND_ERROR,
        /**
         * 连接超时
         */
        TIMEOUT_ERROR,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }
}
