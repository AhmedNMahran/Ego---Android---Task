package com.ahmednmahran.egoshopping.controller.networking;

import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.URI;


public class ServiceGenerator {

    public static final String API_BASE_URL = "https://api.letsbuildthatapp.com/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder()
                                    .create()
                    ));

    public static Retrofit.Builder getBuilder() {
        return builder;
    }

    /**
     * when no auth required
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(
            Class<S> serviceClass) {
        if (!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear();
        }
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                URI uri = original.url().uri();
                String method = original.method();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Content-Type", "application/json")
                        .method(method, original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
