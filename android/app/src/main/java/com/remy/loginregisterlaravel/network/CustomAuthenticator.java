package com.remy.loginregisterlaravel.network;

import android.app.DownloadManager;
import android.media.session.MediaSession;
import android.os.CpuUsageInfo;


import com.remy.loginregisterlaravel.TokenManager;
import com.remy.loginregisterlaravel.entities.AccessToken;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Created by remy on 09/12/2017.
 */

public class CustomAuthenticator implements Authenticator {


    private TokenManager tokenManager;
    private static CustomAuthenticator INSTANCE;

    private CustomAuthenticator(TokenManager tokenManager){
        this.tokenManager = tokenManager;
    }

     static synchronized CustomAuthenticator getInstance(TokenManager tokenManager){

        if(INSTANCE == null){
            INSTANCE = new CustomAuthenticator(tokenManager);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

         if(responseCount(response)>=2){

             return null;
         }

         AccessToken token = tokenManager.getToken();

         ApiService service = RetrofitBuilder.createservice(ApiService.class);
        Call<AccessToken> call = service.refresh(token.getRefreshToken() + "a");
        retrofit2.Response<AccessToken> res = call.execute();

        if(res.isSuccessful()){
            AccessToken newToken  =res.body();
            tokenManager.saveToken(newToken );

            return response.request().newBuilder().header("Auhorization", "Bearer" + res.body().getAccessToken()).build();
        }
        return null;
    }

    private int responseCount(Response response){
         int result  =1;

         while ((response = response.priorResponse()) !=null){
             result++;
         }
         return result;
    }
}
