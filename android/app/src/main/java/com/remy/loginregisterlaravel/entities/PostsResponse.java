package com.remy.loginregisterlaravel.entities;

import java.util.List;

/**
 * Created by remy on 09/12/2017.
 */

public class PostsResponse {

    List<Post> data;

    public List<Post> getData(){

        return data;
    }
}
