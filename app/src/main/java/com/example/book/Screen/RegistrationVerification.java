package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class RegistrationVerification extends AppCompatActivity {

    Button btnGuiOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xac_thuc_dang_ky);
        setControl();
        setAction();
    }

    private void setAction() {
        btnGuiOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void setControl() {
        btnGuiOTP = findViewById(R.id.btnGuiOTP);
    }
}
