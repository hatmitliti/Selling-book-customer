package com.example.book.Screen;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.Adapter.AdapterVoucher;
import com.example.book.MainActivity;
import com.example.book.Object.Voucher;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListVoucher extends AppCompatActivity {

    ListView lvVoucher;
    Button btnDongYVoucher;
    ArrayList<Voucher> listVoucher = new ArrayList<>();
    AdapterVoucher adapterVoucher;
    DatabaseReference database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ma_giam_gia);
        database = FirebaseDatabase.getInstance().getReference("vouchers");
        listVoucher.add(new Voucher("aaaa",10,20));
        listVoucher.add(new Voucher("aaaa",10,20));
        listVoucher.add(new Voucher("aaaa",10,20));
       // System.out.println("alo alo");
        adapterVoucher = new AdapterVoucher(getApplicationContext(), R.layout.item_voucher_listview, listVoucher);
        setControl();
        setAction();

    }

    private void setAction() {
        // chọn voucher
        lvVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int a = 0;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (a == 0) {
                    view.setBackgroundColor(Color.RED);
                } else {
                    view.setBackgroundColor(Color.WHITE);
                }
                a = 1;
            }
        });


        // lay danh sach voucher
        database.child(MainActivity.usernameApp).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Voucher voucher = snapshot.getValue(Voucher.class);
                listVoucher.add(voucher);

                adapterVoucher.notifyDataSetChanged();
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

    private void setControl() {
        lvVoucher = findViewById(R.id.lvVoucher);
        btnDongYVoucher = findViewById(R.id.btnDongYVoucher);
    }
}
