package com.iolll.liubo.retrofitrxjava.model.net;

import com.iolll.liubo.retrofitrxjava.model.bean.GankData;
import com.iolll.liubo.retrofitrxjava.model.bean.GankResult;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by LiuBo on 2018/5/25.
 */


public interface NetService {
    final String BASE_URL = "http://gank.io/api/";




    @GET("data/{type}/{limit}/{page}")
    Observable<GankResult<ArrayList<GankData>>> getType(@Path("type") String type,
                                                        @Path("limit") String limit,
                                                        @Path("page") int page);


}
