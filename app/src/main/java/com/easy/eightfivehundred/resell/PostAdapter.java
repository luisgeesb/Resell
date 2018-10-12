package com.easy.eightfivehundred.resell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(context).inflate(R.layout.post_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, final int position) {
        //final String note = noteList.get(position);
        final Post post = postList.get(position);


        holder.locationTextView.setText(post.getLocation());
        holder.priceTextView.setText(String.valueOf(post.getPrice()));
        holder.titleTextView.setText(post.getTitle());
        Glide.with(context).load(post.getImage()).into(holder.imageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewPostIntent = new Intent(context, ViewPostActivity.class);
                viewPostIntent.putExtra("post_name", post.getName());
                context.startActivity(viewPostIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, priceTextView, locationTextView;
        RelativeLayout parentLayout;
        ImageView imageView;

        public PostViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.title_textview);
            priceTextView = view.findViewById(R.id.price_textview);
            locationTextView = view.findViewById(R.id.location_textview);
            imageView = view.findViewById(R.id.imageView);
            parentLayout = view.findViewById(R.id.parent_layout);
        }
    }

}
