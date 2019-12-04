package com.example.takeaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

class LauncherManager {
    private static String IS_FIRST_TIME = "isFirst";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    LauncherManager(Context context) {
        String PREF_NAME = "LunchManger";
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }

    void setFirstLunch() {
        editor.putBoolean(IS_FIRST_TIME, false);
        editor.commit();
    }

    boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }
}

