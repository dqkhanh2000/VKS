package com.sict.mobile.vks.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sict.mobile.vks.interfaces.ServerAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtils {

    public static final String BASE_URL = "http://192.168.61.27:5000/";
    private static Retrofit sRetrofit = null;

    public static Retrofit getClient(){
        if(sRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return sRetrofit;
    }

    public static ServerAPI getAPI(){
        return getClient().create(ServerAPI.class);
    }
}
