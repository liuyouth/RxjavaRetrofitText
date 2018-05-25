package com.iolll.liubo.retrofitrxjava.model.bean;

/**
 * Created by LiuBo on 2018/5/25.
 */

public class GankResult<T>  {

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
