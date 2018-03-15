package com.domain;

/**
 * Created by mujiang on 2017/11/28.
 */
public class FunObject<T> {

    private T data;
    public FunObject() {
    }

    public FunObject(T data) {this.data = data;}

    public T getData() {
        return data;
    }

}
