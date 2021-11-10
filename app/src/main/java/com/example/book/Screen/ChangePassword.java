package com.example.book.Screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.R;

public class ChangePassword extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtNhapMatKhau, txtNhapMatKhauMoi, txtNhapLaiMatKhauMoi;
    private Button btnDoiMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doi_mat_khau);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mapping();
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void mapping() {
        txtNhapMatKhau = findViewById(R.id.txtNhapMatKhau);
        txtNhapMatKhauMoi = findViewById(R.id.txtNhapMatKhauMoi);
        txtNhapLaiMatKhauMoi = findViewById(R.id.txtNhapLaiMatKhauMoi);
        btnDoiMK = findViewById(R.id.btnDoiMK);
        toolbar = findViewById(R.id.toolbar_ChangePassword);
    }
}