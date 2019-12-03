package com.example.takeaction.firebase;

import com.google.firebase.auth.FirebaseUser;

public interface DataCallback {
    void onAuthSuccess(FirebaseUser user);
}
