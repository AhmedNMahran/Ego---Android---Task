package com.ahmednmahran.egoshopping.controller.networking;

import com.ahmednmahran.egoshopping.model.Upselling;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ahmed Nabil on 12/17/18.
 */
public interface ApiService {

    @GET("appstore/featured")
    Call<Upselling> featchuredApps();

}
