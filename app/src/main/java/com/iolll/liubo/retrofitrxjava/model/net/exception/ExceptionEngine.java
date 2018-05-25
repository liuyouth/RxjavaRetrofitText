package com.iolll.liubo.retrofitrxjava.model.net.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.BAD_GATEWAY;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.FORBIDDEN;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.GATEWAY_TIMEOUT;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.INTERNAL_SERVER_ERROR;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.NOT_FOUND;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.REQUEST_TIMEOUT;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.SERVICE_UNAVAILABLE;
import static com.iolll.liubo.retrofitrxjava.model.net.exception.ERROR.UNAUTHORIZED;


/**
 * 异常转换
 * Created by LiuBo on 2018/5/18.
 */

public class ExceptionEngine {


    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch(httpException.code()){
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.setDisplayMessage("网络错误");  //均视为网络错误
                    break;            }
            return ex;
        } else if (e instanceof ServerException){    //服务器返回的错误
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, resultException.getCode());
            ex.setDisplayMessage(resultException.getMsg());
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.setDisplayMessage("解析错误");            //均视为解析错误
            return ex;
        }else if(e instanceof SocketTimeoutException || e instanceof UnknownHostException || e instanceof ConnectException){
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.setDisplayMessage("连接失败");  //均视为网络错误
            return ex;
        }else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.setDisplayMessage("未知错误");          //未知错误
            return ex;
        }

    }
}
