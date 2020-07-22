package com.sict.mobile.vks.utils;

import com.sict.mobile.vks.ServerAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtils {

    public static final String BASE_URL = "http://192.168.61.27:5000/";
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient(){
        if(sRetrofit == null)
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return sRetrofit;
    }

    public static ServerAPI getAPI(){
        return getClient().create(ServerAPI.class);
    }
}
