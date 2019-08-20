package com.example.hqsv4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hqsv4.OOP.Info;
import com.example.hqsv4.Infomation;
import com.example.hqsv4.R;

import java.util.List;

public class AdapterInformation extends BaseAdapter {
    private Infomation context;
    private int layout;
    private List<Info> infoLists;


    public AdapterInformation(Infomation context, int layout, List<Info> infoLists) {
        this.context = context;
        this.layout = layout;
        this.infoLists = infoLists;
    }

    @Override
    public int getCount() {
        return infoLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder
    {
        TextView inforList;
        TextView datafields;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(layout,null);
            //Ánh xạ qua holder
            holder.inforList = (TextView) convertView.findViewById(R.id.inforList);
            holder.datafields = (TextView)convertView.findViewById(R.id.datafields);
            convertView.setTag(holder);
        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        final Info info = infoLists.get(position);

        holder.inforList.setText(info.getInfo());
        holder.datafields.setText(info.getDatafields());
        return convertView;
    }
}
