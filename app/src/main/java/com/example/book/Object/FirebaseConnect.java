package com.example.book.Object;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseConnect {
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

    public static void setStartProduct(Evalute evalute) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("products");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                if (product.getId().equals(evalute.getId_product())) {
                    double startTotal = product.getStar() + evalute.getStar();
                    DatabaseReference mDatabaseProduct = FirebaseDatabase.getInstance().getReference("products");
                    mDatabaseProduct.child(product.getId()).child("star").setValue(startTotal);
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
