package com.hybrid.tech.mylocationv4;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.zip.Inflater;

public class MyThemeAdapter extends RecyclerView.Adapter<MyThemeAdapter.ViewHolder> {

    int[] imageID;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView cardView;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView=itemView;
        }
    }
    private Listener listener;

    public static interface Listener
    {
        public void onClick(int position);
    }

    public void setListener(Listener listener)
    {
        this.listener=listener;
    }

    public MyThemeAdapter(int[]imageId)
    {

        this.imageID=imageId;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv=(CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_theme_adapter,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CardView cardView=holder.cardView;
        ImageView imageView=(ImageView)cardView.findViewById(R.id.info_image);
        Drawable drawable=cardView.getResources().getDrawable(imageID[position]);
        imageView.setImageDrawable(drawable);

        cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
       return imageID.length;
    }



}
