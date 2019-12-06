package com.example.takeaction.splash;

import android.content.Context;
import android.content.SharedPreferences;

class LauncherManager {
    private static String PREF_NAME = "LunchManger";
    private static String IS_FIRST_TIME = "isFirst";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    LauncherManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = sharedPreferences.edit();
        editor.apply();

    }

    void setFirstLunch(boolean isFirst) {
        editor.putBoolean(IS_FIRST_TIME, isFirst);
        editor.commit();
    }

    boolean isFirstTime() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }
}