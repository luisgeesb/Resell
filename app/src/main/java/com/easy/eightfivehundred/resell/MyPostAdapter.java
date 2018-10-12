package com.easy.eightfivehundred.resell;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder>{

    private Context context;
    private List<Post> myPostList;

    public MyPostAdapter(Context context, List<Post> myPostList) {
        this.context = context;
        this.myPostList = myPostList;
    }

    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostViewHolder(
                LayoutInflater.from(context).inflate(R.layout.my_post_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostAdapter.MyPostViewHolder holder, final int position) {
        //final String note = noteList.get(position);
        final Post post = myPostList.get(position);

        holder.titleTextView.setText(post.getTitle());
        Glide.with(context).load(post.getImage()).into(holder.imageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent viewPostIntent = new Intent(context, ViewPostActivity.class);
//                viewPostIntent.putExtra("post_name", post.getName());
//                context.startActivity(viewPostIntent);
                Toast.makeText(context, "Click!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPostList.size();
    }

    class MyPostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        RelativeLayout parentLayout;
        ImageView imageView;

        public MyPostViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.my_post_title);
            imageView = view.findViewById(R.id.my_post_image);
            parentLayout = view.findViewById(R.id.my_post_parent_layout);
        }
    }

}