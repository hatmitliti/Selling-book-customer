package com.example.book.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book.Object.Product;
import com.example.book.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapterProductHome extends RecyclerView.Adapter<MyRecyclerViewAdapterProductHome.MyHolderViewProductHome> {
    private Activity context;
    private int layoutID;
    private ArrayList<Product> arrayListProduct;

    public MyRecyclerViewAdapterProductHome(Activity context, int layoutID, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.layoutID = layoutID;
        this.arrayListProduct = arrayListProduct;
    }

    @NonNull
    @Override
    public MyHolderViewProductHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        CardView view = (CardView) layoutInflater.inflate(viewType, parent, false);
        return new MyHolderViewProductHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderViewProductHome holder, int position) {
        Product product = arrayListProduct.get(position);
        holder.imgHinhAnhSach.setImageResource(product.getHinhAnh());
        holder.txtGiaSach.setText(product.getGiaTien());
        holder.txtTenSach.setText(product.getTenSanPham());

    }

    @Override
    public int getItemViewType(int position) {
        return layoutID;
    }

    @Override
    public int getItemCount() {
        return arrayListProduct.size();
    }

    public static class MyHolderViewProductHome extends RecyclerView.ViewHolder {

        private ImageView imgHinhAnhSach;
        private TextView txtTenSach;
        private TextView txtGiaSach;

        public MyHolderViewProductHome(@NonNull View itemView) {
            super(itemView);
            imgHinhAnhSach = itemView.findViewById(R.id.imgHinhAnhSach1);
            txtTenSach = itemView.findViewById(R.id.txtTenSach1);
            txtGiaSach = itemView.findViewById(R.id.txtGiaSach1);

        }
    }
}
