package com.ahmednmahran.egoshopping.controller.settings;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

/**
 * Created by Ahmed Nabil on 12/14/18.
 * This Singleton Class is responsible for saving and loading local data from shared preferences
 */
public class AppPreference {
    private static AppPreference instance;
    private static Context context;
    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;

    private AppPreference(){

        settings = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static AppPreference getInstance(Context context) {
        if (instance == null)
        {
            AppPreference.context = context;
            instance = new AppPreference();
        }
        return instance;
    }

    public void save(Object object){
        editor.putString(object.getClass().getSimpleName(),new Gson().toJson(object.toString())).commit();
    }

    public String getString(Object object){
        return settings.getString(object.getClass().getSimpleName(),null);
    }

}
