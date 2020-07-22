package com.sict.mobile.vks.interfaces;

public interface CallBackListener {
    void onDone();
    void onDone(Object obj);
    void onError(String message);
}
