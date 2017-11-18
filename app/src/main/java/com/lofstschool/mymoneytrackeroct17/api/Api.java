package com.lofstschool.mymoneytrackeroct17.api;



import com.lofstschool.mymoneytrackeroct17.Item;
import com.lofstschool.mymoneytrackeroct17.Result.AddResult;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Api {

    @GET ("items")
    Call<List<Item>> items (@Query("type") String type);

    @POST("items/add")
    Call<AddResult> add(@Query("name") String name, @Query("price") int price, @Query("type") String type);


}
