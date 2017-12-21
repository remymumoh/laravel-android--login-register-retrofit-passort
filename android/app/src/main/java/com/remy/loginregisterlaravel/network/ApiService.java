package com.remy.loginregisterlaravel.network;

import com.remy.loginregisterlaravel.entities.AccessToken;
import com.remy.loginregisterlaravel.entities.PostsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by remy on 27/11/2017.
 */

public interface ApiService{


    @POST("register")
    @FormUrlEncoded
    Call<AccessToken>register(@Field("name") String name, @Field("email")String email, @Field("password") String password);


    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token")String refreshToken);


    @GET("posts")

    Call<PostsResponse>posts();
}














