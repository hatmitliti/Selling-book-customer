package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.Adapter.CustomAdapterEvalute;
import com.example.book.MainActivity;
import com.example.book.Object.FirebaseConnect;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Evalute extends AppCompatActivity {

    TextView txtMaBill;
    GridView lvDanhGia;
    ArrayList<FirebaseConnect.ProductCart> list = new ArrayList<>();
    ArrayList<String> idListProduct = new ArrayList<>();
    Button btnXong;
    CustomAdapterEvalute customAdapterEvalute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_gia);
        setControl();
        setText();
        getDataInDatabase();
        customAdapterEvalute = new CustomAdapterEvalute(getApplicationContext(), R.layout.item_danh_gia_sach, list, idListProduct);
        lvDanhGia.setAdapter(customAdapterEvalute);

        btnXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("bills");
                mDatabase.child(getIntent().getStringExtra("id_billsss")).child("evalute").setValue(true);
                finish();
            }
        });



        // toolbarr
        Toolbar toolbar = findViewById(R.id.tbChangePassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void getDataInDatabase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("bill_detail");
        mDatabase.child(getIntent().getStringExtra("id_billsss")).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseConnect.ProductCart productCart = snapshot.getValue(FirebaseConnect.ProductCart.class);
                list.add(productCart);
                idListProduct.add(snapshot.getKey());
                customAdapterEvalute.notifyDataSetChanged();
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

    private void setText() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id_billsss");
        txtMaBill.setText("Mã: " + id);
    }

    private void setControl() {
        txtMaBill = findViewById(R.id.txtMaBill);
        lvDanhGia = findViewById(R.id.lvDanhGia);
        btnXong = findViewById(R.id.btnXong);
    }
}
