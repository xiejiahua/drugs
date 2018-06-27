package com.example.develop2.drugs.api;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by xiejiahua on 2018/6/5.
 */

public interface UserClient {

    @FormUrlEncoded
    @POST("user_login")
    Call<JsonObject> login(@Field("mobile") String phone, @Field("pwd") String passwrod);

    @Multipart
    @POST("wx_login")
    Call<JsonObject> wxLogin(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("get_mobilecode")
    Call<JsonObject> fetchCaptcha(@Field("mt") String mt, @Field("mc") String mc, @Field
            ("mobile") String phone);

    @Multipart
    @POST("ins_register")
    Call<JsonObject> resgister(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("upd_userpwd")
    Call<JsonObject> changePassword(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("upd_forgotpwd")
    Call<JsonObject> forgetPassword(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST("get_regmc")
    Call<JsonObject> getRegMc(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("get_forgotmc")
    Call<JsonObject> getForgetMc(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("get_upot")
    Call<JsonObject> getUserPot(@Field("uid") String uid);

}

