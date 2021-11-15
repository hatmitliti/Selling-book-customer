package com.example.book.Screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.MainActivity;
import com.example.book.Object.User;
import com.example.book.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUser extends AppCompatActivity {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        CircleImageView profile_image = findViewById(R.id.profile_image);
        EditText txtNameUserEdit = findViewById(R.id.txtNameUserEdit);
        EditText txtBirthUser = findViewById(R.id.txtBirthUser);
        EditText edtDiaChi = findViewById(R.id.tvUpdateAddressUser);
        TextView txtphoneUser_ = findViewById(R.id.txtphoneUser_);
        Button btnLuu = findViewById(R.id.btnLuu);
       // Button btnBack = findViewById(R.id.backInforUser);

        //lấy dữ liệu:
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("users");
        data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(MainActivity.usernameApp)) {
                    user = snapshot.getValue(User.class);
                    try {
                        Picasso.get().load(user.getImage()).into(profile_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    txtNameUserEdit.setText(user.getName());
                    txtBirthUser.setText(user.getBirth());
                    txtphoneUser_.setText(user.getPhone());
                    edtDiaChi.setText(user.getAddress());
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

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameUserEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (txtBirthUser.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (txtphoneUser_.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edtDiaChi.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    user.setBirth(txtBirthUser.getText().toString());
                    user.setName(txtNameUserEdit.getText().toString());
                    user.setPhone(txtphoneUser_.getText().toString());
                    user.setAddress(edtDiaChi.getText().toString());

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(MainActivity.usernameApp).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                }

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
}