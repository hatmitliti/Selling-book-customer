package com.example.customer.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customer.R;

public class Register extends AppCompatActivity {

    Button btnDangKy1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_tai_khoan);
        setControl();
        setAction();
    }

    private void setAction() {
        btnDangKy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationVerification.class));
            }
        });
    }

    private void setControl() {
        btnDangKy1 = findViewById(R.id.btnDangKy1);
    }

}
