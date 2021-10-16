package com.example.book.Screen;

import static android.os.Debug.waitForDebugger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button btnDangKyTaiKhoan;
    TextView txtHoVaTen, txtMail, txtNgaySinh, txtPasswordRegister, txtRePasswordRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_tai_khoan);
        setControl();
        setAction();
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
//                        auth.sendSignInLinkToEmail(username,actionCodeSettings).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(getApplicationContext(), "Đã gửi xác thực đến email của bạn, Vui lòng xác thực!", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
                                startActivity(new Intent(getApplicationContext(), Login.class));
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
