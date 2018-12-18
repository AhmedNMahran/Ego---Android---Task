package com.ahmednmahran.egoshopping.controller.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import com.ahmednmahran.egoshopping.model.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stripe.android.model.Card;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ahmed Nabil on 12/14/18.
 * This Singleton Class is responsible for saving and loading local data from shared preferences
 */
public class AppPreference {
    public static final String AR = "ar";
    public static final String EN = "en";
    private static AppPreference instance;
    private static Context context;
    private final SharedPreferences settings;
    private final SharedPreferences.Editor editor;
    private Gson gson;

    private AppPreference() {

        settings = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE);
        editor = settings.edit();
        gson = new Gson();
    }

    public static void init(Context context) {
        AppPreference.context = context;
    }

    public static AppPreference getInstance() {
        if (instance == null)
            instance = new AppPreference();
        return instance;
    }

    public AppPreference save(Object object) {
        editor.putString(object.getClass().getSimpleName(), new Gson().toJson(object.toString())).commit();
        return this;
    }

    public AppPreference saveCard(Card card) {
        editor.putString("card", new Gson().toJson(card)).commit();
        return this;
    }
    public Card getCard() {
        String card = settings.getString("card", "");
        Card savedCard = null;
        if(!card.isEmpty()){
            try{
                savedCard = gson.fromJson(card,Card.class);
            }catch (Exception e){
                return savedCard;
            }
        }
        return savedCard;
    }

    public String getObjectString(Class object) {
        return settings.getString(object.getSimpleName(), null);
    }

    public User getSavedUser() {
        String savedUser = settings.getString("savedUser", "");
        User user = new User("علي العلي", 23.0, "", new LatLng(21.553279, 39.166201), "");
        if (!savedUser.isEmpty()) {
            try {
                user = gson.fromJson(savedUser, User.class);
            } catch (JsonSyntaxException e) {
                return user;
            }
        }
        return user;
    }

    public Product getSavedProduct() {
        String savedProduct = settings.getString("savedProduct", "");
        Product product = new Product("المنتج العجيب", 120, getSavedStore(), 30, new Date());
        if (!savedProduct.isEmpty()) {
            try {
                product = gson.fromJson(savedProduct, Product.class);
            } catch (JsonSyntaxException e) {
                return product;
            }
        }
        return product;
    }

    public App getUpSellingProduct() {
        String savedProduct = settings.getString("upSellingProduct", "");
        App product = null;
        if (!savedProduct.isEmpty()) {
            try {
                product = gson.fromJson(savedProduct, App.class);
            } catch (JsonSyntaxException e) {
                return product;
            }
        }
        return product;
    }

    public AppPreference saveSelectedUpSelling(App product) {
        String prodString = "";
        if (product != null)
            prodString = gson.toJson(product);
        editor.putString("upSellingProduct", prodString).commit();
        return this;
    }

    public Store getSavedStore() {
        String savedStore = settings.getString("savedStore", "");
        Store store = new Store("أ ب ت المحدودة ", "المملكة العربیة السعودیة 23326 السراي، الأندلس، جدة ", "As Sairi, Al Andalus, Jeddah 23326, Saudi Arabia", new LatLng(21.553279, 39.166201));
        if (!savedStore.isEmpty()) {
            try {
                store = gson.fromJson(savedStore, Store.class);
            } catch (JsonSyntaxException e) {
                return store;
            }
        }
        return store;
    }

    public AppPreference saveProduct(Product product) {
        editor.putString("savedProduct", gson.toJson(product)).commit();
        return this;
    }

    public AppPreference updateUser(User user) {
        editor.putString("savedUser", gson.toJson(user)).commit();
        return this;
    }

    public AppPreference saveStore(Store store) {
        editor.putString("savedStore", gson.toJson(store)).commit();
        return this;
    }

    public Locale getLocale() {
        return new Locale(getLocaleLanguage());
    }

    private String getLocaleLanguage() {
        return settings.getString("locale", AR);
    }

    public AppPreference setLocale(String language) {
        editor.putString("locale", language).commit();
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        return this;
    }

    public AppPreference updateLocale() {
        setLocale(getLocaleLanguage());
        return this;
    }

    public boolean isFirstRun() {
        return settings.getBoolean("isFirstRun", true);
    }

    public AppPreference setAppLaunched() {
        editor.putBoolean("isFirstRun", false).commit();
        return this;
    }

}
