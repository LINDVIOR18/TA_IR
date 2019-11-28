package com.example.takeaction;

import android.content.Context;
import android.content.SharedPreferences;

class LuncherManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static String PREF_NAME = "LunchManger";
    private static String IS_FIRST_TIME = "isFirst";

    LuncherManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        editor = sharedPreferences.edit();
    }

    void setFirstLunch(boolean isFirst){
        editor.putBoolean(IS_FIRST_TIME,isFirst);
        editor.commit();
    }

    boolean isFirstTime(){
        return sharedPreferences.getBoolean(IS_FIRST_TIME,true);
    }
}

