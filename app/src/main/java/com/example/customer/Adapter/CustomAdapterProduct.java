package com.example.customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customer.Object.Product;
import com.example.customer.R;

import java.util.ArrayList;

public class CustomAdapterProduct extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Product> data;


    public CustomAdapterProduct(Context context, int resource, ArrayList<Product> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.giaTien = convertView.findViewById(R.id.txtGiaSach1);
            viewHolder.tenSach = convertView.findViewById(R.id.txtTenSach1);
            viewHolder.imgHinhAnh = convertView.findViewById(R.id.imgHinhAnhSach1);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product pc = data.get(position);
        viewHolder.giaTien.setText(pc.getGiaTien());
        viewHolder.tenSach.setText(pc.getTenSanPham());
        viewHolder.imgHinhAnh.setImageResource(pc.getHinhAnh());
        return convertView;
    }

    // viewholder
    private static class ViewHolder {
        TextView giaTien;
        TextView tenSach;
        ImageView imgHinhAnh;

    }
}
