package com.example.book.Object;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirebaseConnect {

    public static ArrayList<Product> getAllProduct() {
        ArrayList<Product> list = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                Object object = snapshot.getValue();



                System.out.println(snapshot.getValue().toString());

              //  list.add(new Product(R.drawable.toan12, "", "", "", "", "", "", "", "", ""));




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}
