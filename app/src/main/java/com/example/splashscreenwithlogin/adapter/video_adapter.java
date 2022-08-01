package com.example.splashscreenwithlogin.adapter;

import static com.example.splashscreenwithlogin.Interface.ApiConstants.PICASSO_VIDEO_BASE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.VideoBitmapDecoder;
import com.bumptech.glide.request.RequestOptions;
import com.example.splashscreenwithlogin.Display_Portal;
import com.example.splashscreenwithlogin.model.Video;
import com.example.splashscreenwithlogin.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class video_adapter extends RecyclerView.Adapter<video_adapter.ViewHolder>{

    Context context;
    private ArrayList<Video> videos;

    public video_adapter(Context context, ArrayList<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public video_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_video_view, parent, false);
        return new video_adapter.ViewHolder(itemView,context);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull video_adapter.ViewHolder holder, int position) {
        Video app_video = videos.get(position);
        holder.video_heading.setText(Html.fromHtml(app_video.getTitle()));

        Glide.with(context)
                .load(PICASSO_VIDEO_BASE_URL + app_video.getVideo())
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE));
                holder.video_view1.setVideoURI(Uri.parse(PICASSO_VIDEO_BASE_URL + app_video.getVideo()));
                MediaController mediaController = new MediaController(context.getApplicationContext());
                holder.video_view1.setMediaController(mediaController);
                mediaController.setAnchorView(holder.video_view1);
                holder.video_view1.requestFocus();
                holder.video_view1.start();
                holder.video_view1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.video_heading)
        TextView video_heading;


        @BindView(R.id.video_view1)
        VideoView video_view1;


        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}