package com.sict.mobile.vks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sict.mobile.vks.interfaces.CallBackListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUtils {
    private static final String PREFS_NAME = "user_prefs";
    private static boolean hasLogin = false, hasData = false;
    private static String email, name;
    private static int id;
    private static SharedPreferences  prefs;

    @SerializedName("id")
    public int mId;

    @SerializedName("has_data")
    public int mHasData;


    public static boolean isHasData() {
        hasData = prefs.getBoolean("hasData", false);
        return hasData;
    }

    public static boolean isHasLogin() {
        hasLogin = prefs.getBoolean("hasLogin", false);
        return hasLogin;
    }

    public static String getEmail() {
        email = prefs.getString("email", null);
        return email;
    }

    public static String getName() {
        name = prefs.getString("name", null);
        return name;
    }

    public static int getId() {
        id = prefs.getInt("id", -1);
        return id;
    }

    public static void setHasData() {
        prefs.edit().putBoolean("hasData", true).apply();
        UserUtils.hasData = true;
    }

    public static void setLogOut(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", "");
        editor.putString("name", "");
        editor.putBoolean("hasLogin", false);
        editor.putInt("id", -1);
        editor.putBoolean("hasData", false);
        editor.apply();
    }

    public static void setLogin(String email, String name, Context context, CallBackListener callBack){
        prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        hasLogin = true;
        UserUtils.email = email;
        UserUtils.name = name;
        writeToSharePrefer(callBack);
    }

    public static void init(Context context){
        prefs = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        email = prefs.getString("email", null);
        name = prefs.getString("name", null);
        id = prefs.getInt("id", -1);
        hasLogin = prefs.getBoolean("hasLogin", false);
        hasData = prefs.getBoolean("hasData", false);
    }

    private static void writeToSharePrefer(CallBackListener callBack) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("name", name);
        editor.putBoolean("hasLogin", hasLogin);
        APIUtils.getAPI().getUserId(email, name).enqueue(new Callback<UserUtils>() {
            @Override
            public void onResponse(Call<UserUtils> call, Response<UserUtils> response) {
                if(response.isSuccessful()){
                    editor.putInt("id", response.body().mId);
                    editor.putBoolean("hasData", response.body().mHasData == 1);
                    editor.apply();
                    callBack.onDone();
                }

            }

            @Override
            public void onFailure(Call<UserUtils> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
}
