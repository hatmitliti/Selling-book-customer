package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.MainActivity;
import com.example.book.Object.User;
import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button btnDangKyTaiKhoan;
    TextView txtHoVaTen, txtMail, txtNgaySinh, txtPasswordRegister, txtRePasswordRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_sign_up);
        setControl();
        setAction();
        // toolbarr
        Toolbar toolbar = findViewById(R.id.tbChangePassword);
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

    private void setAction() {

        // bấm nút đăng ký tài khoản
        btnDangKyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoVaTen = (String) txtHoVaTen.getText().toString();
                String mail = (String) txtMail.getText().toString();
                String ngaySinh = (String) txtNgaySinh.getText().toString();
                String password = (String) txtPasswordRegister.getText().toString();
                String rePassword = (String) txtRePasswordRegister.getText().toString();

                if (!password.equals(rePassword)) {
                    Toast.makeText(getApplicationContext(), "Xác nhận mật khẩu không đúng!", Toast.LENGTH_SHORT).show();

                } else if (hoVaTen.isEmpty() || mail.isEmpty() || ngaySinh.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else {
                    // đăng ký tk vào firebase
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // cập nhật nội dung vào firebase:

                                User user = new User("", ngaySinh, 0, hoVaTen, "", "Đồng", "","");
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference("users");
                                database.child(auth.getUid()).setValue(user);
                                MainActivity.usernameApp = auth.getUid();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Đăng kí không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //  startActivity(new Intent(getApplicationContext(), RegistrationVerification.class));
            }
        });
    }

    private void setControl() {
        btnDangKyTaiKhoan = findViewById(R.id.btnDangKyTaiKhoan);
        txtHoVaTen = findViewById(R.id.txtHoVaTen);
        txtMail = findViewById(R.id.txtMail);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtPasswordRegister = findViewById(R.id.txtPasswordRegister);
        txtRePasswordRegister = findViewById(R.id.txtRePasswordRegister);
    }

}
