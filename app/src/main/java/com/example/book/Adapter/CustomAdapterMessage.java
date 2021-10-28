package com.example.book.Adapter;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book.Object.Message;
import com.example.book.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterMessage extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Message> data;

    public CustomAdapterMessage(Context context, int resource, ArrayList<Message> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Message message = data.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.itemTinNhan = convertView.findViewById(R.id.itemTinNhan);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemTinNhan.setText(data.get(position).getContent());
        if (message.getWho().equals("admin")) {
            viewHolder.itemTinNhan.setGravity(Gravity.LEFT);
        } else {
            viewHolder.itemTinNhan.setGravity(Gravity.RIGHT);
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public static class ViewHolder {
        TextView itemTinNhan;
    }
}
