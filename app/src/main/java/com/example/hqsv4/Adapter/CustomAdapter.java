package com.example.hqsv4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hqsv4.Infomation;
import com.example.hqsv4.OOP.HQSV;
import com.example.hqsv4.OOP.Users;
import com.example.hqsv4.R;

import java.util.List;

public  class CustomAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Users> user;

    public CustomAdapter(Context context, int layout, List<Users> user) {
        this.context = context;
        this.layout = layout;
        this.user = user;
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        ImageView imgHinh;
        TextView name;

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgHinh = convertView.findViewById(R.id.hinh);
            holder.name = convertView.findViewById(R.id.ten);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Users user1 = user.get(position);
//        holder.imgHinh.setImageResource();
        Glide.with(context).load("https://graph.facebook.com/v4.0/"+user1.getIdFb()+"/picture?height=500&width=500&migration_overrides=%7Boctober_2012%3Atrue%7D").into(holder.imgHinh);
        holder.name.setText(user1.getName());
            return convertView;
    }
}

