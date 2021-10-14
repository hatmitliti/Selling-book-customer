package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;

public class DetailBook extends AppCompatActivity {
    ImageView imgMessageInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_sach);
        setControl();
        setAction();
    }

    private void setAction() {
        imgMessageInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MessageUserScreen.class));

            }
        });
    }

    private void setControl() {
        imgMessageInbox = findViewById(R.id.imgMessageInbox);
    }
}
