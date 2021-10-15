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
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.Product;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PKIXRevocationChecker;
import java.util.ArrayList;

public class HomeProduct extends Fragment {

    ArrayList<Product> listProduct = new ArrayList<>();
    CustomAdapterProduct adapterProduct;
    DatabaseReference dataProduct;
//    ArrayList<String> mKey = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataProduct = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.home_product, container, false);


        adapterProduct = new CustomAdapterProduct(getContext(), R.layout.item_product_listview, listProduct);
        GridView grProduct = view.findViewById(R.id.grProduct);
        grProduct.setAdapter(adapterProduct);


        grProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "123", Toast.LENGTH_SHORT).show();
            }
        });
//        FirebaseConnect.getAllProduct();
        // lấy mảng product
        // Toast.makeText(getContext(), FirebaseConnect.getAllProduct(), Toast.LENGTH_SHORT).show();\

//        Product pd = new Product("https://firebasestorage.googleapis.com/v0/b/selling-books-ba602.appspot.com/o/rungnauy.jpg?alt=media&token=46131688-7f09-4fd9-92f4-3361605e8076","s2","Rừng Nauy",200000,"Tiểu Thuyết",4.5,0,0,"Sách XYZ","Murakami Haruki");
//
//        dataProduct.child("products").push().setValue(pd);

        dataProduct.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product pd = snapshot.getValue(Product.class);
                listProduct.add(pd);
                adapterProduct.notifyDataSetChanged();

//                String key = snapshot.getKey();
//                mKey.add(key);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String key = snapshot.getKey();
//                int index = mKey.indexOf(key);
//                Toast.makeText(getContext(), index, Toast.LENGTH_SHORT).show();
//                if (snapshot.getKey().equals("hinhAnh") || snapshot.getKey().equals("author") || snapshot.getKey().equals("category") || snapshot.getKey().equals("id")
//                        || snapshot.getKey().equals("tenSanPham") ||snapshot.getKey().equals("description")) {
//
//                    String value = snapshot.getValue(String.class);
//
//                    switch (snapshot.getKey()) {
//                        case "hinhAnh":
//                            listProduct.get(index).setHinhAnh(value);
//
//                            break;
//                        case "author":
//                            listProduct.get(index).setAuthor(value);
//
//                            break;
//                        case "category":
//                            listProduct.get(index).setCategory(value);
//
//                            break;
//                        case "id":
//                            listProduct.get(index).setId(value);
//
//                            break;
//                        case "tenSanPham":
//                            listProduct.get(index).setTenSanPham(value);
//
//                            break;
//                        case "description":
//                            listProduct.get(index).setDescription(value);
//
//                            break;
//                    }
//                    adapterProduct.notifyDataSetChanged();
//                }
//                else if (snapshot.getKey().equals("star"))
//                {
//                    double data = (double) snapshot.getValue();
//                    listProduct.get(index).setStar(data);
//                    adapterProduct.notifyDataSetChanged();
//                }
//                else {
//                    int data = (int) snapshot.getValue();
//                    switch (snapshot.getKey()) {
//                        case "sold":
//                            listProduct.get(index).setSold(data);
//
//                            break;
//                        case "stock":
//                            listProduct.get(index).setStock(data);
//
//                            break;
//                        case "giaTien":
//                            listProduct.get(index).setGiaTien(data);
//
//                            break;
//                    }
//                    adapterProduct.notifyDataSetChanged();
//                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }


}
