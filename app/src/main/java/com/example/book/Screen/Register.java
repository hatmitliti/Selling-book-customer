package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.MainActivity;
import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    Button btnDangKy1;
    TextView edtEmail, edtPassword, edtRe_enterPassword;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_tai_khoan);
        setControl();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        setAction();
    }

    private void setAction() {
        btnDangKy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String username = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String repass = edtRe_enterPassword.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tài khoản!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
        } else if (repass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
        } else if (!repass.equals(password)) {
            Toast.makeText(getApplicationContext(), "Vui lòng xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        auth.signOut();
                    } else {
                        Toast.makeText(getApplicationContext(), "Đăng kí không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setControl() {
        btnDangKy1 = findViewById(R.id.btnDangKy1);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRe_enterPassword = findViewById(R.id.edtRe_enterPassword);
    }
}
