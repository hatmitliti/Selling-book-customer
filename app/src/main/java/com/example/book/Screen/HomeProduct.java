package com.example.book.Screen;


import static com.example.book.Object.Product.ProductComparatorGiaTienHightoLow;
import static com.example.book.Object.Product.ProductComparatorGiaTienLowtoHigh;
import static com.example.book.Object.Product.ProductComparatorStarHightoLow;
import static com.example.book.Object.Product.ProductComparatorStarLowtoHigh;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class HomeProduct extends Fragment {

    ArrayList<Product> listProduct = new ArrayList<>();
    CustomAdapterProduct adapterProduct;
    DatabaseReference dataProduct;
    ArrayList<String> mKey = new ArrayList<>();
    ArrayList<String> listCateory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_product, container, false);

        // Hiển thị danh sách sản phẩm
        dataProduct = FirebaseDatabase.getInstance().getReference();
        adapterProduct = new CustomAdapterProduct(getContext(), R.layout.item_product_listview, listProduct);
        GridView grProduct = view.findViewById(R.id.grProduct);
        grProduct.setAdapter(adapterProduct);


        // Hiển thị lọc loại sản phẩm:
        Spinner spLoaiSanPham = view.findViewById(R.id.spLoaiSanPhamLoc);
        listCateory = new ArrayList<String>();
        listCateory.add("Tất cả");
        ArrayAdapter adapterCategory = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listCateory);
        spLoaiSanPham.setAdapter(adapterCategory);
        // lọc
        spLoaiSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String locCategory = spLoaiSanPham.getSelectedItem().toString();
                for (int j = 0; j < listProduct.size(); j++) {
                    if (listProduct.get(j).getCategory().equals(locCategory)) {
                        listProduct.remove(j);
                        adapterProduct.notifyDataSetChanged();
                    }
                }
                if (locCategory.equals("Tất cả")) {
                    listProduct.clear();
                    getDatafirebaseProduct();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String locCategory = spLoaiSanPham.getSelectedItem().toString();
                Toast.makeText(getContext(), locCategory, Toast.LENGTH_SHORT).show();
            }
        });

        // Hiển thị lọc giá tiền và số sao
        Spinner spLocSauLoai = view.findViewById(R.id.spLocSauLoai);
        ArrayList<String> listFillMoney = new ArrayList<String>();
        listFillMoney.add("Không lọc");
        listFillMoney.add("Lọc theo giá từ cao xuống thấp");
        listFillMoney.add("Lọc theo giá từ thấp xuống cao");
        listFillMoney.add("Lọc theo đánh giá cao");
        listFillMoney.add("Lọc theo đánh giá thấp");
        ArrayAdapter adapterFillMoney = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listFillMoney);
        spLocSauLoai.setAdapter(adapterFillMoney);
        //loc
        spLocSauLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String locSauLoai = spLocSauLoai.getSelectedItem().toString();
                if (locSauLoai.equals("Lọc theo giá từ cao xuống thấp")) {

                    Collections.sort(listProduct, ProductComparatorGiaTienHightoLow);
                    adapterProduct.notifyDataSetChanged();

                } else if (locSauLoai.equals("Lọc theo giá từ thấp xuống cao")) {

                    Collections.sort(listProduct, ProductComparatorGiaTienLowtoHigh);
                    adapterProduct.notifyDataSetChanged();

                } else if (locSauLoai.equals("Lọc theo đánh giá cao")) {

                    Collections.sort(listProduct, ProductComparatorStarHightoLow);
                    adapterProduct.notifyDataSetChanged();

                } else if (locSauLoai.equals("Lọc theo đánh giá thấp")) {

                    Collections.sort(listProduct, ProductComparatorStarLowtoHigh);
                    adapterProduct.notifyDataSetChanged();

                } else {
                    Collections.shuffle(listProduct);
                    adapterProduct.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Search sản phẩm
        EditText txtSearchProductInHome = view.findViewById(R.id.txtSearchProductInHome);
        txtSearchProductInHome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = txtSearchProductInHome.getText().toString();
                if (text.equals("")) {
                    listProduct.clear();
                    getDatafirebaseProduct();
                } else {
                    for (int j = 0; j < listProduct.size(); j++) {
                        if (!listProduct.get(j).getTenSanPham().equals(text)) {
                            listProduct.remove(j);

                        }
                    }
                }
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        // click vào sản phẩm đến trang chi tiết
        grProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailBook.class);

                intent.putExtra("imgProduct", listProduct.get(i).getHinhAnh());
                intent.putExtra("idProduct", listProduct.get(i).getId());
                intent.putExtra("nameProduct", listProduct.get(i).getTenSanPham());
                intent.putExtra("priceProduct", listProduct.get(i).getGiaTien() + "");
                intent.putExtra("descriptionProduct", listProduct.get(i).getDescription());
                intent.putExtra("stockProduct", listProduct.get(i).getStock() + "");
                intent.putExtra("categoryProduct", listProduct.get(i).getCategory());
                intent.putExtra("authorProduct", listProduct.get(i).getAuthor());

                startActivity(intent);
            }
        });

        return view;
    }

    private void getDatafirebaseProduct() {
        // lấy dữ liệu product từ firebase
        dataProduct.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product pd = snapshot.getValue(Product.class);
                pd.setId(snapshot.getKey());
                listProduct.add(pd);

                // Lấy loại sp bỏ vào list spinner lọc
                if (!listCateory.contains(pd.getCategory())) {
                    listCateory.add(pd.getCategory());
                }


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
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                listProduct.remove(index);
                adapterProduct.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    private void sapXep(ArrayList<Product> list) {
        for (int j = 0; j < list.size(); j++) {
            for (int k = j + 1; k < list.size(); k++) {
                if (list.get(j).getGiaTien() > list.get(k).getGiaTien()) {
                    Product temp = list.get(0);

                }
            }
        }
    }

}
