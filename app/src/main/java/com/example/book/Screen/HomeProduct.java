package com.example.book.Screen;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterProduct;
import com.example.book.Object.Product;
import com.example.book.R;

import java.util.ArrayList;

public class HomeProduct extends Fragment {
  
    ArrayList<Product> listProduct = new ArrayList<>();
    CustomAdapterProduct adapterProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_product, container, false);


        listProduct.add(new Product(R.drawable.toan12, "Lịch sử", "100 000"));
        listProduct.add(new Product(R.drawable.toan12, "Lịch sử", "100 000"));
        listProduct.add(new Product(R.drawable.toan12, "Lịch sử", "100 000"));

        adapterProduct = new CustomAdapterProduct(getContext(), R.layout.item_product_listview, listProduct);
        GridView grProduct = view.findViewById(R.id.grProduct);
        grProduct.setAdapter(adapterProduct);

        
        grProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
            }
        });
        
        return view;
    }


}
