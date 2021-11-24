package com.example.book.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterProductSeen;
import com.example.book.MainActivity;
import com.example.book.Object.Product;
import com.example.book.Object.User;
import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ProfileActivity extends Fragment {
    TextView txtPhoneUser,txtAddressUser,txtRankUser,txtNameUser;
    Button btnTrangThaiDonHangUser,btnInfo,btnDoiMatKhau,btnSignOut,btnForgotPassword;
    User user;
    ImageView imgUser;
    String idUserCurrent;
    Context context;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    GridView gvSpDaXem;
    ArrayList<Product> list;
    CustomAdapterProductSeen adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        context = view.getContext();
        // set control
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnTrangThaiDonHangUser = view.findViewById(R.id.btnTrangThaiDonHangUser);
        txtAddressUser = view.findViewById(R.id.txtAddressUser);
        txtPhoneUser = view.findViewById(R.id.txtPhoneUser);
        txtRankUser = view.findViewById(R.id.txtRankUser);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        imgUser = view.findViewById(R.id.imgUser);
        gvSpDaXem = view.findViewById(R.id.gvSpDaXem);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnInfo = view.findViewById(R.id.btnInfo);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnForgotPassword = view.findViewById(R.id.btnForgotPassword);
        TextView txtTotalMoneyUser = view.findViewById(R.id.txtTotalMoneyUser);


        // lấy thông tin user:
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                txtNameUser.setText(user.getName());
                txtPhoneUser.setText("Số điện thoại: " + user.getPhone());
                txtAddressUser.setText("Địa chỉ: " + user.getAddress());
                txtRankUser.setText("Hạng thành viên: " + user.getRank());
                idUserCurrent = snapshot.getKey();
                txtTotalMoneyUser.setText(NumberFormat.getInstance().format(user.getMoneyBuy()));
                if (user.getImage().equals("")) {
                    imgUser.setImageResource(R.drawable.user);
                } else {
                    Picasso.get().load(user.getImage()).into(imgUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // gán các layout sản phẩm đã xem
        list = new ArrayList<>();
        adapter = new CustomAdapterProductSeen(getContext(), R.layout.item_product_listview_seen, list);
        gvSpDaXem.setAdapter(adapter);

        // set action
        btnTrangThaiDonHangUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderStatus.class));
            }
        });

        // Bấm vào ds đã xem đi đến trang chi tiết :
        gvSpDaXem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailBook.class);

                intent.putExtra("imgProduct", list.get(position).getHinhAnh());
                intent.putExtra("idProduct", list.get(position).getId());
                intent.putExtra("nameProduct", list.get(position).getTenSanPham());
                intent.putExtra("priceProduct", list.get(position).getGiaTien() + "");
                intent.putExtra("descriptionProduct", list.get(position).getDescription());
                intent.putExtra("stockProduct", list.get(position).getStock() + "");
                intent.putExtra("categoryProduct", list.get(position).getCategory());
                intent.putExtra("authorProduct", list.get(position).getAuthor());

                startActivity(intent);
            }
        });
        // bấm vào nút đổi mật khẩu:
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });

        // bấm vào nút cập nhật thông tin user
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InfoUser.class));
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), SignInActivity.class));
                getActivity().finishAffinity();
            }
        });
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                mAuth.sendPasswordResetEmail(mUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        // lấy ds các sản phẩm đã xem:
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("product_seens");
        data.child(mUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                list.add(snapshot.getValue(Product.class));
                adapter.notifyDataSetChanged();
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
