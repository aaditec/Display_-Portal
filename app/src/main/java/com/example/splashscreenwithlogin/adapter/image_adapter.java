package com.example.splashscreenwithlogin.adapter;
import static com.example.splashscreenwithlogin.Interface.ApiConstants.PICASSO_BASE_URL;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.splashscreenwithlogin.Display_Portal;
import com.example.splashscreenwithlogin.model.Images;
import com.example.splashscreenwithlogin.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class image_adapter extends RecyclerView.Adapter<image_adapter.ViewHolder>{

    Context context;
    private ArrayList<Images> images;

    public image_adapter(Context context, ArrayList<Images> imageviews) {
        this.context = context;
        this.images = imageviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_view, parent, false);
        return new ViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Images app_image = images.get(position);
        holder.image_heading.setText(Html.fromHtml(app_image.getTitle()));
        Glide.with(context).load(PICASSO_BASE_URL + app_image.getImage()).into(holder.imageView);
//        Picasso.get().load( PICASSO_BASE_URL + app_image.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        System.out.println("null mathi");
        return images.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_heading)
        TextView image_heading;

        @BindView(R.id.image_view)
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}