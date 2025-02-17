package com.example.fragmentsbonus.network;

public interface NetworkCallBack {
    void onSuccess(Object response);
    void onError(String error);
}
