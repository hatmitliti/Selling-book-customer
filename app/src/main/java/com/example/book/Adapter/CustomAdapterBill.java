package com.example.book.Adapter;

import static com.example.book.R.drawable.bg_circle_solid_blue;
import static com.example.book.R.drawable.bg_circle_solid_green;
import static com.example.book.R.drawable.bg_circle_solid_grey;
import static com.example.book.R.drawable.bg_circle_solid_yellow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.Object.Bill;
import com.example.book.R;

import java.util.ArrayList;

public class CustomAdapterBill extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Bill> list;

    public CustomAdapterBill(Context context, int resource, ArrayList<Bill> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);


        LinearLayout linnear_itemBill = convertView.findViewById(R.id.linnear_itemBill);
        TextView txtDiaChiBillStatus = convertView.findViewById(R.id.txtDiaChiBillStatus);
        TextView txtTenNguoiNhanBillStatus = convertView.findViewById(R.id.txtTenNguoiNhanBillStatus);
        TextView txtMaDonHangBillStatus = convertView.findViewById(R.id.txtMaDonHangBillStatus);
        TextView txtTienTraBillStatus = convertView.findViewById(R.id.txtTienTraBillStatus);
        TextView txtTrangThaiDonHang = convertView.findViewById(R.id.txtTrangThaiDonHang);


        txtDiaChiBillStatus.setText("Địa chỉ: " + list.get(position).getAddress());
        txtTenNguoiNhanBillStatus.setText("Người nhận: " + list.get(position).getName());
        txtMaDonHangBillStatus.setText("Mã: " + list.get(position).getId());
        txtTienTraBillStatus.setText("Tiền trả: " + (list.get(position).getTotalMoney() - list.get(position).getDiscount()));

        if (list.get(position).getStatus() == 0) {
            Drawable d = context.getResources().getDrawable(bg_circle_solid_blue);
            linnear_itemBill.setBackgroundDrawable(d);
            txtTrangThaiDonHang.setText("Chờ xác nhận");
        } else if (list.get(position).getStatus() == 1 || list.get(position).getStatus() == 2 || list.get(position).getStatus() == 3 || list.get(position).getStatus() == 4) {
            Drawable d = context.getResources().getDrawable(bg_circle_solid_green);
            linnear_itemBill.setBackgroundDrawable(d);
            txtTrangThaiDonHang.setText("Đang giao");
        } else if (list.get(position).getStatus() == 5 || list.get(position).getStatus() == 6 || list.get(position).getStatus() == 7) {
            Drawable d = context.getResources().getDrawable(bg_circle_solid_grey);
            linnear_itemBill.setBackgroundDrawable(d);
            txtTrangThaiDonHang.setText("Đã giao");
        } else if (list.get(position).getStatus() == -1 || list.get(position).getStatus() == 8 || list.get(position).getStatus() == 9 || list.get(position).getStatus() == 10) {
            Drawable d = context.getResources().getDrawable(bg_circle_solid_yellow);
            linnear_itemBill.setBackgroundDrawable(d);
            txtTrangThaiDonHang.setText("Đã hủy");
        }


        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
