package com.example.takeaction.firebase;

public interface AuthDataCallback<T> {
    void onSuccess(T response);
    void onError();
}
