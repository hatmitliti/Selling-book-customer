package com.example.book.Object;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.UUID;

public class FirebaseConnect {

    public static void addProductInCart(String usernameApp, Product product) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
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
                pd = snapshot.child(mUser.getUid()).child(product.getId()).getValue(ProductInCart.class);

                if (pd != null) {
                    pd.setNumberCart(pd.getNumberCart() + 1);
                    mDatabase.child(mUser.getUid()).child(product.getId()).setValue(pd);
                } else {
                    mDatabase.child(mUser.getUid()).child(product.getId()).setValue(productInCart);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void setCheckedProductInCart(ProductInCart productInCart) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        mDatabase.child(mUser.getUid()).child(productInCart.
                getId()).child("chkbox").setValue(productInCart.isChkbox());
    }

    public static void setQualytyLow(ProductInCart productInCart) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (!(productInCart.getNumberCart() == 0)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
            mDatabase.child(mUser.getUid()).child(productInCart.
                    getId()).child("numberCart").setValue(productInCart.getNumberCart() - 1);
        }

    }

    public static void setQualytyHigh(ProductInCart productInCart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(productInCart.getId())) {
                    Product product = snapshot.getValue(Product.class);
                    int cart = productInCart.getNumberCart();
                    int pd = product.getStock();
                    if (cart == pd) {

                    } else {
                        FirebaseDatabase.getInstance().getReference("carts").child(mUser.getUid()).child(productInCart.
                                getId()).child("numberCart").setValue(productInCart.getNumberCart() + 1);

                    }

                }
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


    }

    public static void deleteProductInCart(ProductInCart productInCart) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("carts");
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase.child(mUser.getUid()).child(productInCart.
                getId()).removeValue();
    }

    public static void createBill(Bill bill, ArrayList<ProductInCart> list) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("bills");
        mDatabase.child(bill.getId()).setValue(bill);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        // thêm vào bill_detail
        DatabaseReference mDatabaseDetail = FirebaseDatabase.getInstance().getReference("bill_detail").child(bill.getId());
        for (int j = 0; j < list.size(); j++) {
            ProductCart productCart = new ProductCart(list.get(j).getTenSanPham(), list.get(j).getGiaTien(), list.get(j).getNumberCart());
            mDatabaseDetail.child(list.get(j).getId()).setValue(productCart);
        }

        // xóa sản phẩm trong giỏ hàng sau khi mua
        DatabaseReference mDatabaseCart = FirebaseDatabase.getInstance().getReference("carts");
        for (int j = 0; j < list.size(); j++) {
            mDatabaseCart.child(mUser.getUid()).child(list.get(j).getId()).removeValue();
        }

        // cập nhật lại thông tin user:
        DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference("users");
        mDatabaseUser.child(mUser.getUid()).child("address").setValue(bill.getAddress());
        mDatabaseUser.child(mUser.getUid()).child("phone").setValue(bill.getPhone());
    }

    public static class ProductCart {
        private String name;
        private int price;
        private int quality;

        public ProductCart() {
        }

        public ProductCart(String name, int price, int quality) {
            this.name = name;
            this.price = price;
            this.quality = quality;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }
    }

    public static void setStarProduct(Evalute evalute) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                if (product.getId().equals(evalute.getId_product())) {
                    double starTotal = product.getStar() + evalute.getStar();
                    DatabaseReference mDatabaseProduct = FirebaseDatabase.getInstance().getReference("products");
                    mDatabaseProduct.child(product.getId()).child("star").setValue(starTotal);
                    mDatabaseProduct.child(product.getId()).child("numberEvalute").setValue(product.getNumberEvalute() + 1);
                }
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
    }
}
















