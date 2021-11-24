package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.Adapter.CustomAdapterBill;
import com.example.book.MainActivity;
import com.example.book.Object.Bill;
import com.example.book.Object.FirebaseConnect;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderStatus extends AppCompatActivity {

    ListView lvDonHangCanGiao;
    ArrayList<Bill> listBill;
    CustomAdapterBill customAdapterBill;
    ArrayList<String> mKey = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trang_thai_don_hang);
        setControl();
        getDataBill();
        setAction();
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

    private void getDataBill() {
        listBill = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("bills");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bill bill = snapshot.getValue(Bill.class);
                if (bill.getId_user().equals(MainActivity.usernameApp)) {
                    if (!(bill.isEvalute() == true)) {
                        listBill.add(bill);
                        customAdapterBill.notifyDataSetChanged();
                        mKey.add(snapshot.getKey());
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bill bill = snapshot.getValue(Bill.class);
                if (bill.isEvalute()) {
                    for (int j = 0; j < listBill.size(); j++) {
                        if (listBill.get(j).getId() == bill.getId()) {
                            listBill.remove(j);
                            customAdapterBill.notifyDataSetChanged();
                        }
                    }
                }
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
        customAdapterBill = new CustomAdapterBill(getApplicationContext(), R.layout.item_listview_don_hang_cho, listBill);
        lvDonHangCanGiao.setAdapter(customAdapterBill);

    }

    private void setAction() {
        lvDonHangCanGiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listBill.get(i).getStatus() == 5 || listBill.get(i).getStatus() == 6 || listBill.get(i).getStatus() == 7) {
                    Intent intent = new Intent(getApplicationContext(), Evalute.class);
                    intent.putExtra("id_billsss", listBill.get(i).getId());
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Đơn hàng chưa giao nên chưa thể đánh giá ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setControl() {

        lvDonHangCanGiao = findViewById(R.id.lvDonHangCanGiao);
    }
}
