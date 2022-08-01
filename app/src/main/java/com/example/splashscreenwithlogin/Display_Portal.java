package com.example.splashscreenwithlogin;

import static com.example.splashscreenwithlogin.Interface.ApiConstants.PICASSO_VIDEO_BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.splashscreenwithlogin.Interface.API;
import com.example.splashscreenwithlogin.model.Images;
import com.example.splashscreenwithlogin.model.Post;
import com.example.splashscreenwithlogin.model.Video;
import com.example.splashscreenwithlogin.adapter.image_adapter;
import com.example.splashscreenwithlogin.adapter.video_adapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Display_Portal extends AppCompatActivity {
    TextView textView;
    RecyclerView rv_image, rv_video;
    image_adapter imageAdapter;
    video_adapter videoAdapter;
    DownloadManager manager;
    VideoView video_view1;
    TextView video_heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_portal);
        textView = findViewById(R.id.textView);
        rv_image = findViewById(R.id.rv_image);
        video_view1 = findViewById(R.id.video_view1);
        video_heading = findViewById(R.id.video_heading);
        video_view1.setFocusable(false);
        rv_image.setFocusable(false);
        textView.setSelected(true);
        description();
        rv_image_view();
        rv_video_view();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rv_image.setLayoutManager(manager);

        video_view1.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                finish();
                return false;
            }
        });



    }


    private void rv_video_view() {
        Call<ArrayList<Video>> call = API.getClient().getVideo();
        call.enqueue(new Callback<ArrayList<Video>>() {
            @Override
            public void onResponse(Call<ArrayList<Video>> call, Response<ArrayList<Video>> response) {

                if (response.isSuccessful()) {
                    ArrayList<Video> responseImages = response.body();
                    if (responseImages != null) {
                        if (responseImages.size() > 0) {
                            playVideos(responseImages,0,true);
//                            startAsyncFunctiontoDownload(responseImages);


                        }

                    }

                } else if (response.code() == 403) {
                    JSONObject error1 = null;
                    try {
                        error1 = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (error1.has("message")) {
                        try {
                            String resultDescription = error1.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        String resultDescription = "";
                        if (error.has("message")) {
                            resultDescription = error.getString("message");
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Video>> call, Throwable t) {
                Log.d("message", "onFailure: " + t);
            }
        });
    }

    private void playVideos(ArrayList<Video> responseImages,int index, boolean firstOrNot) {
        int initialData = responseImages.size()-1;   //Since we will be working with array we initialise with -1

        if (index>=initialData){
            firstOrNot = true;
            index = 0;
        }
        else {
            if (!firstOrNot){
                index++;
            }
        }

        setVideosInView(responseImages,index);
    }


    private void setVideosInView(ArrayList<Video> responseImages, int index) {
        video_heading.setText(responseImages.get(index).getTitle());
        video_view1.setVideoURI(Uri.parse(PICASSO_VIDEO_BASE_URL + responseImages.get(index).getVideo()));
        MediaController mediaController = new MediaController(Display_Portal.this);
        video_view1.setMediaController(mediaController);
        mediaController.setAnchorView(video_view1);
        video_view1.requestFocus();
        video_view1.start();
        video_view1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playVideos(responseImages,index,false);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void startAsyncFunctiontoDownload(ArrayList<Video> responseImages) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                videoAdapter = new video_adapter(getApplicationContext(), responseImages);
                rv_video.setAdapter(videoAdapter);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            protected Void doInBackground(Void... params) {
                for (Video videos : responseImages) {
                    try {
                        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                        Uri uri = Uri.parse(videos.getVideo());
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//                                long reference = manager.enqueue(request);
                    } catch (Exception e) {

                    }

                }
                return null;
            }
        }.execute();
    }

    private void description() {
        Call<ArrayList<Post>> call = API.getClient().getPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Post> responsetext = response.body();
                    if (responsetext != null) {
                        if (responsetext.size() > 0) {
                            String responseTitles = "";
                            for (Post post : responsetext) {
                                responseTitles = responseTitles + " " + post.getTitle();
                            }
                            textView.setText(responseTitles);
                        }
                    }
                } else if (response.code() == 403) {
                    JSONObject error1 = null;
                    try {
                        error1 = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (error1.has("message")) {
                        try {
                            String resultDescription = error1.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        String resultDescription = "";
                        if (error.has("message")) {
                            resultDescription = error.getString("message");
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d("message", "onFailure: " + t);
            }
        });
    }

    private void rv_image_view() {
        Call<ArrayList<Images>> call = API.getClient().getImage();
        call.enqueue(new Callback<ArrayList<Images>>() {
            @Override
            public void onResponse(Call<ArrayList<Images>> call, Response<ArrayList<Images>> response) {

                if (response.isSuccessful()) {
                    ArrayList<Images> responseImages = response.body();
                    if (responseImages != null) {
                        if (responseImages.size() > 0) {
                             imageAdapter = new image_adapter(getApplicationContext(), responseImages);
                            rv_image.setAdapter(imageAdapter);

                            Handler handler = new Handler();
                            Runnable runnable = new Runnable() {
                                int count = 0;
                                @Override
                                public void run() {
                                    if (count<responseImages.size()){
                                        rv_image.scrollToPosition(count++);
                                        handler.postDelayed(this, 10000); //10 sec delay
                                        if (count==responseImages.size()){
                                            count = 0;
                                        }
                                    }
                                }
                            };
                            handler.postDelayed(runnable,5000);
                        }
                    }
                } else if (response.code() == 403) {
                    JSONObject error1 = null;
                    try {
                        error1 = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (error1.has("message")) {
                        try {
                            String resultDescription = error1.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        JSONObject error = new JSONObject(response.errorBody().string());
                        String resultDescription = "";
                        if (error.has("message")) {
                            resultDescription = error.getString("message");
                        } else {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Images>> call, Throwable t) {
                Log.d("message", "onFailure: " + t);
            }
        });


// play on prepared function

    }

}