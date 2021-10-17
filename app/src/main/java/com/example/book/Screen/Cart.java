package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterProduct;
import com.example.book.Adapter.CustomAdapterProductInCart;
import com.example.book.MainActivity;
import com.example.book.Object.Product;
import com.example.book.Object.ProductInCart;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Cart extends Fragment {

    GridView lvProductInCart;
    Button btnChonVoucherInCart;
    TextView txtTongTienInCart;
    CheckBox chkTatCaInCart;
    Button btnMuaHangInCart;
    CustomAdapterProductInCart adapterProductInCart;
    ArrayList<ProductInCart> listProductInCart;
    DatabaseReference dataProduct;
    ArrayList<String> mKey = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gio_hang, container, false);
        setControl(view);
        setAction();
        setTotalMoney();

        return view;
    }

    private void setAction() {
        listProductInCart = new ArrayList<>();
        getDataInDatabase();

        adapterProductInCart = new CustomAdapterProductInCart(getContext(), R.layout.item_product_in_cart, listProductInCart);
        lvProductInCart.setAdapter(adapterProductInCart);


        // đi đến trang chọn voucher
        btnChonVoucherInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListVoucher.class));
            }
        });

    }

    private void setTotalMoney() {
        int total = 0;
        for (int j = 0; j < listProductInCart.size(); j++) {
            if (listProductInCart.get(j).isChkbox() == true) {
                total += listProductInCart.get(j).getGiaTien() * listProductInCart.get(j).getNumberCart();
            }
        }
        txtTongTienInCart.setText(NumberFormat.getInstance().format(total));
    }

    public void getDataInDatabase() {
        dataProduct.child("carts").child(MainActivity.usernameApp).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductInCart productInCart = snapshot.getValue(ProductInCart.class);
                listProductInCart.add(productInCart);
                mKey.add(snapshot.getKey());
                adapterProductInCart.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // lấy địa chỉ id của đối tượng vừa bị thay đổi bên trong mảng mkey
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                // thay đổi dữ liệu trong gridview giống với dữ liệu trên firebase
                listProductInCart.set(index, snapshot.getValue(ProductInCart.class));
                adapterProductInCart.notifyDataSetChanged();
                setTotalMoney();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                listProductInCart.remove(index);
                mKey.remove(index);
                adapterProductInCart.notifyDataSetChanged();
                setTotalMoney();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl(View view) {
        lvProductInCart = view.findViewById(R.id.lvProductInCart);
        btnChonVoucherInCart = view.findViewById(R.id.btnChonVoucherInCart);
        txtTongTienInCart = view.findViewById(R.id.txtTongTienInCart);
        chkTatCaInCart = view.findViewById(R.id.chkTatCaInCart);
        btnMuaHangInCart = view.findViewById(R.id.btnMuaHangInCart);
        dataProduct = FirebaseDatabase.getInstance().getReference();
    }


}
