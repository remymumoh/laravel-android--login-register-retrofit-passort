package com.remy.loginregisterlaravel.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.remy.loginregisterlaravel.BuildConfig;
import com.remy.loginregisterlaravel.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by remy on 27/11/2017.
 */

public class RetrofitBuilder {

    private static final String BASE_URL = "http://192.168.100.3/public/api/";


    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {



                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();


                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection", "close");


                        request =builder.build();

                        return chain.proceed(request);
                    }
                });

        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client){

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }
    //public ApiCservice.class comes up

    public static <T> T createservice(Class<T> service){

        return retrofit.create(service);
    }

    public static <T> T createServiceWithAuth(Class<T> service, final TokenManager tokenManager){
        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if(tokenManager.getToken().getAccessToken()!=null){
                    builder.addHeader("Authorization", "Bearer" + tokenManager.getToken().getAccessToken());
                }
                request = builder.build();
                return chain.proceed(request);
            }
        }).authenticator(CustomAuthenticator.getInstance(tokenManager))
                .build();


        Retrofit newretrofit = retrofit.newBuilder().client(newClient).build();
        return newretrofit.create(service);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
