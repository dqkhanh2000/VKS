package com.sict.mobile.vks;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServerAPI {
    @POST("add")
    @FormUrlEncoded
    Call<ResponseBody> trainInServer(@Field("id") int id, @Field("data") String data);
}
