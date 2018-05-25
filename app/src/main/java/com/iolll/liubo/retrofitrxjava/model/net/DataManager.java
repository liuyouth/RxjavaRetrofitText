package com.iolll.liubo.retrofitrxjava.model.net;


import com.iolll.liubo.retrofitrxjava.model.bean.GankData;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * 数据管理器
 */
public class DataManager {






    /**
     * 获取首页轮播图
     * @return
     */
    public static Observable<ArrayList<GankData>> getType(String type, int page) {

        return RxRetrofitManager.getInstance().getApiService()
                .getType(type,"10",page )
                .concatMap(RxTransformer.exCloudFailure())
                .compose(RxTransformer.transformer())
                ;
    }





}
