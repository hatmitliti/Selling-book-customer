package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.MainActivity;
import com.example.book.R;

public class Me extends Fragment {

    Button btnTrangThaiDonHangUser;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nguoi_dung, container, false);
        // set control
        btnTrangThaiDonHangUser = view.findViewById(R.id.btnTrangThaiDonHangUser);


        // set action

        // Trần Ngọc Nam
        btnTrangThaiDonHangUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderStatus.class));
            }
        });

        return view;
    }
}
