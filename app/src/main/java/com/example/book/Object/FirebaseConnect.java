package com.example.book.Object;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;

public class FirebaseConnect {

    public static void addProductInCart(String usernameApp, Product product) {
        ProductInCart productInCart = new ProductInCart();
        productInCart.setNumberCart(1);
        productInCart.setGiaTien(product.getGiaTien());
        productInCart.setHinhAnh(product.getHinhAnh());
        productInCart.setId(product.getId());
        productInCart.setTenSanPham(product.getTenSanPham());


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        //  mDatabase.child(usernameApp).child(product.getId()).setValue(productInCart);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductInCart pd;
                pd = snapshot.child(MainActivity.usernameApp).child(product.getId()).getValue(ProductInCart.class);

                if (pd != null) {
                    pd.setNumberCart(pd.getNumberCart() + 1);
                    mDatabase.child(usernameApp).child(product.getId()).setValue(pd);
                } else {
                    mDatabase.child(usernameApp).child(product.getId()).setValue(productInCart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void setCheckedProductInCart(ProductInCart productInCart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        mDatabase.child(MainActivity.usernameApp).child(productInCart.
                getId()).child("chkbox").setValue(productInCart.isChkbox());
    }

    public static void setQualytyLow(ProductInCart productInCart) {
        if (!(productInCart.getNumberCart() == 0)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
            mDatabase.child(MainActivity.usernameApp).child(productInCart.
                    getId()).child("numberCart").setValue(productInCart.getNumberCart() - 1);
        }

    }

    public static void setQualytyHigh(ProductInCart productInCart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        mDatabase.child(MainActivity.usernameApp).child(productInCart.
                getId()).child("numberCart").setValue(productInCart.getNumberCart() + 1);
    }

    public static void deleteProductInCart(ProductInCart productInCart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        mDatabase.child(MainActivity.usernameApp).child(productInCart.
                getId()).removeValue();
    }

}
