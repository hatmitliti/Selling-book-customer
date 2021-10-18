package com.example.book.Screen;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
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
    ArrayList<String> mKey = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_product, container, false);

        // Hiển thị danh sách sản phẩm
        dataProduct = FirebaseDatabase.getInstance().getReference();
        adapterProduct = new CustomAdapterProduct(getContext(), R.layout.item_product_listview, listProduct);
        GridView grProduct = view.findViewById(R.id.grProduct);
        grProduct.setAdapter(adapterProduct);

        // Hiển thị lọc spinner:
        Spinner spLoaiSanPham = view.findViewById(R.id.spLoaiSanPham);
        Spinner spLocSauLoai = view.findViewById(R.id.spLocSauLoai);


        // click vào sản phẩm đến trang chi tiết
        grProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailBook.class);

                intent.putExtra("imgProduct", listProduct.get(i).getHinhAnh());
                intent.putExtra("nameProduct", listProduct.get(i).getTenSanPham());
                intent.putExtra("priceProduct", listProduct.get(i).getGiaTien() + "");
                intent.putExtra("descriptionProduct", listProduct.get(i).getDescription());
                intent.putExtra("stockProduct", listProduct.get(i).getStock() + "");
                intent.putExtra("categoryProduct", listProduct.get(i).getCategory());
                intent.putExtra("authorProduct", listProduct.get(i).getAuthor());

                startActivity(intent);

            }
        });

        // lấy dữ liệu product từ firebase
        dataProduct.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product pd = snapshot.getValue(Product.class);
                listProduct.add(pd);
                adapterProduct.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

        // lấy dữ liệu product từ firebase
        dataProduct.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product pd = snapshot.getValue(Product.class);
                listProduct.add(pd);
                adapterProduct.notifyDataSetChanged();
                // lấy id của các sản phẩm
                String key = snapshot.getKey();
                mKey.add(key);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // lấy địa chỉ id của đối tượng vừa bị thay đổi bên trong mảng mkey
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                // thay đổi dữ liệu trong gridview giống với dữ liệu trên firebase
                listProduct.set(index, snapshot.getValue(Product.class));
                adapterProduct.notifyDataSetChanged();
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
