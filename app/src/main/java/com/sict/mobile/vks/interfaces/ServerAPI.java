package com.sict.mobile.vks.interfaces;

import com.sict.mobile.vks.utils.UserUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("add")
    @FormUrlEncoded
    Call<ResponseBody> trainInServer(@Field("id") int id, @Field("data") String data);

    @POST("get-id")
    @FormUrlEncoded
    Call<UserUtils> getUserId(@Field("email") String email, @Field("name") String name);

    @GET("get-model")
    Call<ResponseBody> getModel();
}
