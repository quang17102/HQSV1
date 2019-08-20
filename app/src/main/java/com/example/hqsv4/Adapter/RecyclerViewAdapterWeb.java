package com.example.hqsv4.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hqsv4.R;
import com.example.hqsv4.WebView;

import java.util.ArrayList;

public class RecyclerViewAdapterWeb extends RecyclerView.Adapter<RecyclerViewAdapterWeb.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapterWeb";
    public static String urlIntent = "url_Intent";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> urlWeb = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterWeb(Context context, ArrayList<String> imageNames, ArrayList<String> images, ArrayList<String> urlweb ) {
        mImageNames = imageNames;
        urlWeb = urlweb;
        mImages = images;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_index, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + urlWeb.get(position));

//                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, WebView.class);
                intent.putExtra(urlIntent, urlWeb.get(position));
//                intent.putExtra("image_name", mImageNames.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.CircleImage);
            imageName = itemView.findViewById(R.id.text_image);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}















