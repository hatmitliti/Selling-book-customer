package com.example.customer.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customer.MainActivity;
import com.example.customer.R;
import com.example.customer.Screen.Register;


public class Login extends AppCompatActivity {
    Button btnDangKyTaiKhoan, btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setAction();
    }

    private void setAction() {
        // bấm đăng ký
        btnDangKyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
        // bấm đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void setControl() {
        btnDangKyTaiKhoan = findViewById(R.id.btnDangKyTaiKhoan);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }

}
