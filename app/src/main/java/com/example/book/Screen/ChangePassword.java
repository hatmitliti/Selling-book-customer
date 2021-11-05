package com.example.book.Screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class ChangePassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doi_mat_khau);

        TextView txtNhapMatKhau = findViewById(R.id.txtNhapMatKhau);
        TextView txtNhapMatKhauMoi = findViewById(R.id.txtNhapMatKhauMoi);
        TextView txtNhapLaiMatKhauMoi = findViewById(R.id.txtNhapLaiMatKhauMoi);
        Button btnDoiMK = findViewById(R.id.btnDoiMK);

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}