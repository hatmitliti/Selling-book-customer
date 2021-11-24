package com.example.book.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FogotPass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_pass);

        EditText txtmailFogotPass = findViewById(R.id.txtmailFogotPass);
        Button btnGuiMail = findViewById(R.id.btnGuiMail);

        btnGuiMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = txtmailFogotPass.getText().toString().trim();
                if (mail.equals("")) {
                    Toast.makeText(getApplicationContext(), "Yêu càu nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Gửi email đổi mật khẩu thành công. Vui lòng kiểm tra email!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toast.makeText(getApplicationContext(), "Gửi email đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // toolbarr
        Toolbar toolbar = findViewById(R.id.toolbar);
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