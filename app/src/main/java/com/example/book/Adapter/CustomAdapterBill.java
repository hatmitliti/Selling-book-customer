package com.example.book.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public CustomAdapterBill(Context context, int resource,ArrayList<Bill> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);

        TextView txtTrangThaiDonHang = convertView.findViewById(R.id.txtTrangThaiDonHang);
        TextView txtDiaChiBillStatus = convertView.findViewById(R.id.txtDiaChiBillStatus);
        TextView txtTenNguoiNhanBillStatus = convertView.findViewById(R.id.txtTenNguoiNhanBillStatus);
        TextView txtMaDonHangBillStatus = convertView.findViewById(R.id.txtMaDonHangBillStatus);
        TextView txtTienTraBillStatus = convertView.findViewById(R.id.txtTienTraBillStatus);
        Bill bill = list.get(position);
        txtMaDonHangBillStatus.setText("Mã: " + bill.getId());
        txtDiaChiBillStatus.setText("Địa chỉ: "+ bill.getAddress());
        txtTenNguoiNhanBillStatus.setText("Tên người nhận: "+ bill.getName());
        txtTienTraBillStatus.setText("Tiền trả: "+ (bill.getTotalMoney() - bill.getDiscount()));


        if(bill.getStatus() == 0){
            txtTrangThaiDonHang.setText("Chờ xác nhận");
        }else if(bill.getStatus() == 1 || bill.getStatus() == 2 || bill.getStatus() == 3||bill.getStatus()==4){
            txtTrangThaiDonHang.setText("Đang giao");
        }else if(bill.getStatus() == 5 || bill.getStatus() == 6|| bill.getStatus() == 7){
            txtTrangThaiDonHang.setText("Đã giao");
        }else if(bill.getStatus()== -1|| bill.getStatus() == 8|| bill.getStatus() == 9|| bill.getStatus() == 10){
            txtTrangThaiDonHang.setText("Đã hủy");
        }

        return convertView;

    }

    @Override
    public int getCount() {
        return list.size();
    }
}
