package com.iolll.liubo.retrofitrxjava.model.bean;

/**
 * Created by LiuBo on 2018/5/25.
 */

/**
 * 统一API响应结果封装
 */
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }


}