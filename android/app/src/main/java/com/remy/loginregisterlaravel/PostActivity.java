package com.remy.loginregisterlaravel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.remy.loginregisterlaravel.entities.PostsResponse;
import com.remy.loginregisterlaravel.network.ApiService;
import com.remy.loginregisterlaravel.network.RetrofitBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends AppCompatActivity {


    private static final String TAG = "PostActivity";

    @BindView(R.id.post_title)
    TextView title;

    ApiService service;
    TokenManager tokenManager;
    Call<PostsResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post);

        ButterKnife.bind(this);

        tokenManager = TokenManager.getINSTANCE(getSharedPreferences("prefs", MODE_PRIVATE));

        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

    }

    @OnClick(R.id.btn_posts)
    void getposts(){

        call = service.posts();
        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                Log.w(TAG, "onResponse"  + response);

                if(response.isSuccessful()){
                    title.setText(response.body().getData().get(0).getTitle());

                }else{
                    startActivity(new Intent(PostActivity.this, LoginActivity.class));
                    finish();
                    tokenManager.deleteToken();

                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Log.w(TAG, "onFailure" + t.getMessage());

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(call != null){

            call.cancel();

            call = null;
        }
    }
}
