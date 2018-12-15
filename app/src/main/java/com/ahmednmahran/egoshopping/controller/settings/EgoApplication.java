package com.ahmednmahran.egoshopping.controller.settings;

import android.app.Application;

/**
 * Created by Ahmed Nabil on 12/15/18.
 */
public class EgoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the AppPreference with the application context
        AppPreference.init(this);
        AppPreference appPreference = AppPreference.getInstance();
        // check if this is the first run, then set default language to arabic
        if (appPreference.isFirstRun()) {
            appPreference.setAppLaunched();
            appPreference.setLocale(AppPreference.AR);
        } else {
            appPreference.updateLocale();
        }
    }
}
