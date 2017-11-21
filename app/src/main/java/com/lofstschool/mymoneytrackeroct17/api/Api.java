package com.lofstschool.mymoneytrackeroct17.api;


import com.lofstschool.mymoneytrackeroct17.Item;
import com.lofstschool.mymoneytrackeroct17.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Api {

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String socialUserId);

    @GET ("items")
    Call<List<Item>> items (@Query("type") String type);

    @GET("balance")
    Call<BalanceResult> balance();

    @POST("items/add")
    Call<AddResult> add(@Query("name") String name, @Query("price") int price, @Query("type") String type);

    @POST("items/remove")
    Call<Result> remove(@Query("id") int id);



}
