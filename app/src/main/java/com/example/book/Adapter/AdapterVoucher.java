package com.example.book.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.Object.Product;
import com.example.book.Object.Voucher;
import com.example.book.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVoucher extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Voucher> data;


    public AdapterVoucher(Context context, int resource, ArrayList<Voucher> data) {
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.txtPhanTramGiam = convertView.findViewById(R.id.txtPhanTramGiam);
            viewHolder.txtGiamToiDa = convertView.findViewById(R.id.txtGiamToiDa);
            viewHolder.imgHinhAnhVoucher = convertView.findViewById(R.id.imgHinhAnhVoucher);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Voucher voucher = data.get(position);
        viewHolder.txtGiamToiDa.setText(voucher.getMaximum() + " VND");
        viewHolder.txtPhanTramGiam.setText(voucher.getPercent() + " %");

        Picasso.get().load("https://epz24x4zq6r.exactdn.com/wp-content/uploads/2020/12/fnal-logo.png?strip=all&lossy=1&ssl=1").
                into(viewHolder.imgHinhAnhVoucher);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgHinhAnhVoucher;
        TextView txtGiamToiDa;
        TextView txtPhanTramGiam;
    }
}
