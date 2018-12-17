package com.ahmednmahran.egoshopping.controller.networking;

/**
 * Created by Ahmed Nabil on 12/17/18.
 */
public class Callback {

    public interface NetworkCallback<T> {
        void onSuccess(T returnedData);
        void onFailure(String message);
    }
}
