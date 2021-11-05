package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.book.MainActivity;
import com.example.book.Object.User;
import com.example.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Me extends Fragment {
    Button btnTTDonHang;
    TextView txtNameUser,txtAddressUser,txtPhoneUser,txtRankUser;
    ImageView imgUser;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nguoi_dung, container, false);
        btnTTDonHang = view.findViewById(R.id.btnTrangThaiDonHangUser);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        txtAddressUser = view.findViewById(R.id.txtAddressUser);
        txtPhoneUser = view.findViewById(R.id.txtPhoneUser);
        txtRankUser = view.findViewById(R.id.txtRankUser);
        imgUser = view.findViewById(R.id.imgUser);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(MainActivity.usernameApp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                txtNameUser.setText(user.getName());
                txtPhoneUser.setText("Số điện thoại: "+ user.getPhone());
                txtAddressUser.setText("Địa chỉ: "+ user.getAddress());
                txtPhoneUser.setText("Hạng thành viên: "+ user.getRank());
                if(user.getImage().equals("")){

                }else {
                    Picasso.get().load(user.getImage()).into(imgUser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnTTDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),OrderStatus.class));
            }
        });
        return view;
    }
}
