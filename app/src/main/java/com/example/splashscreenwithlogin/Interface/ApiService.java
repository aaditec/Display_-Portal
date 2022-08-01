package com.example.splashscreenwithlogin.Interface;

import com.example.splashscreenwithlogin.model.Images;
import com.example.splashscreenwithlogin.model.Post;
import com.example.splashscreenwithlogin.model.Video;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService  {
    @GET(ApiConstants.IMAGE_DETAILS)
     Call<ArrayList<Images>> getImage();

    @GET(ApiConstants.VIDEO_DETAILS)
    Call<ArrayList<Video>> getVideo();

    @GET(ApiConstants.TEXT_DETAILS)
    Call<ArrayList<Post>> getPosts();
}
