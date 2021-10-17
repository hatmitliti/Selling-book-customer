package com.example.book.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.Product;
import com.example.book.Object.ProductInCart;
import com.example.book.R;
import com.example.book.Screen.Cart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CustomAdapterProductInCart extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<ProductInCart> list;

    public CustomAdapterProductInCart(Context context, int resource, ArrayList<ProductInCart> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.chkbox = convertView.findViewById(R.id.chkItemProductInCart);
            viewHolder.txtTenSP = convertView.findViewById(R.id.txtTenProductInCart);
            viewHolder.soLuong = convertView.findViewById(R.id.txtSoLuongProductInCart);
            viewHolder.hinhAnh = convertView.findViewById(R.id.imgHinhAnhProductInCart);
            viewHolder.giaTien = convertView.findViewById(R.id.txtGiaTienInCart);
            viewHolder.btnXoa = convertView.findViewById(R.id.btnXoaProductInCart);
            viewHolder.btnGiam = convertView.findViewById(R.id.btnGiamProductInCart);
            viewHolder.btnTang = convertView.findViewById(R.id.btnTangProductInCart);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProductInCart productInCart = list.get(position);

        viewHolder.chkbox.setChecked(productInCart.isChkbox());
        viewHolder.txtTenSP.setText(productInCart.getTenSanPham());
        viewHolder.soLuong.setText("x" + productInCart.getNumberCart());
        Picasso.get().load(productInCart.getHinhAnh().toString()).into(viewHolder.hinhAnh);
        viewHolder.giaTien.setText(NumberFormat.getInstance().format(productInCart.getGiaTien()));


        // chọn vào sản phẩm
        viewHolder.chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productInCart.setChkbox(viewHolder.chkbox.isChecked());
                FirebaseConnect.setCheckedProductInCart(productInCart);
            }
        });

        // giảm số lượng:
        viewHolder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseConnect.setQualytyLow(productInCart);
            }
        });
        // tăng số lượng:
        viewHolder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseConnect.setQualytyHigh(productInCart);
            }
        });

        // xóa:
        viewHolder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseConnect.deleteProductInCart(productInCart);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    private static class ViewHolder {
        CheckBox chkbox;
        TextView txtTenSP;
        TextView soLuong;
        ImageView hinhAnh;
        TextView giaTien;
        Button btnTang;
        Button btnGiam;
        Button btnXoa;
    }
}
