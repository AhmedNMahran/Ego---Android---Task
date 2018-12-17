package com.ahmednmahran.egoshopping.controller.networking

import com.ahmednmahran.egoshopping.model.App
import com.ahmednmahran.egoshopping.model.Upselling
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Ahmed Nabil on 12/17/18.
 */
class ApiFacade private constructor() {
    private var apiService: ApiService? = null

    init {
        if (apiService == null)
            apiService = ServiceGenerator.createService(
                ApiService::class.java
            )
    }


    fun getUpSellingData(callback: Callback.NetworkCallback<List<App>>) {
        val call = apiService!!.featchuredApps()
        call.enqueue(object : retrofit2.Callback<Upselling> {
            override fun onResponse(call: Call<Upselling>, response: Response<Upselling>) {
                val upselling = response.body()
                val apps = upselling?.categories?.filter { it.name == "Best New Apps"}?.distinct()
                callback.onSuccess(apps?.get(0)?.apps)
            }

            override fun onFailure(call: Call<Upselling>, t: Throwable) {
                callback.onFailure(t.localizedMessage)

            }
        })
    }

    companion object {
        private var instance: ApiFacade = ApiFacade()
        fun getInstance() : ApiFacade {
            return instance
        }
    }

}
