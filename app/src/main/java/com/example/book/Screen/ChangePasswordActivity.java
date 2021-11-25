package com.example.book.Screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.Dialog.NotificationDialog;
import com.example.book.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseAuth auth;
    EditText edtCurrentPassword;
    EditText edtNewPassword;
    EditText edtRePassword;
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        notificationDialog = new NotificationDialog(this);
        edtCurrentPassword = findViewById(R.id.txtNhapMatKhau);
        edtNewPassword = findViewById(R.id.txtNhapMatKhauMoi);
        edtRePassword = findViewById(R.id.txtNhapLaiMatKhauMoi);
        Button btnDoiMK = findViewById(R.id.btnDoiMK);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAuthenticationPassword();
            }
        });
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

    private void reAuthenticationPassword() {
        String email = user.getEmail();
        String password = edtCurrentPassword.getText().toString().trim();

        if (password.isEmpty()) {
            edtCurrentPassword.setError(getResources().getString(R.string.field_empty));
        } else {
            notificationDialog.startLoadingDialog();
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        changePassword();
                    } else {
                        notificationDialog.endLoadingDialog();
                        notificationDialog.startSuccessfulDialog(getResources().getString(R.string.authen_password_failed));
                    }
                }
            });
        }
    }

    private void changePassword() {
        user = auth.getCurrentUser();
        String password = edtNewPassword.getText().toString().trim();
        String repassword = edtRePassword.getText().toString().trim();
        if (password.isEmpty()) {
            edtNewPassword.setError(getResources().getString(R.string.field_empty));
        } else if (repassword.isEmpty()) {
            edtRePassword.setError(getResources().getString(R.string.field_empty));
        } else if (!repassword.equalsIgnoreCase(password)) {
            edtRePassword.setError(getResources().getString(R.string.authen_failed));
        } else {
            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        notificationDialog.endLoadingDialog();
                        notificationDialog.startSuccessfulDialog(getResources().getString(R.string.change_password_success));
                    } else {
                        notificationDialog.endLoadingDialog();
                        notificationDialog.startSuccessfulDialog(getResources().getString(R.string.change_password_failed));
                    }
                }
            });
        }
    }

}