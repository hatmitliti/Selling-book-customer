package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.R;
import com.squareup.picasso.Picasso;

public class DetailBook extends AppCompatActivity {
    ImageView imgMessageInbox;
    ImageView imgAddCartChiTietSach;
    ImageView imgHinhAnhChiTietSach;
    TextView nameProduct;
    TextView priceProduct;
    TextView descriptionProduct;
    TextView stockProduct;
    TextView categoryProduct;
    TextView authorProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_sach);
        setControl();

        setData();
        setAction();

    }


    private void setData() {

        // Lấy dữ liệu
        Intent intent = getIntent();
        String img = intent.getStringExtra("imgProduct");
        String name = intent.getStringExtra("nameProduct");
        String price = intent.getStringExtra("priceProduct");
        String description = intent.getStringExtra("descriptionProduct");
        String stock = intent.getStringExtra("stockProduct");
        String category = intent.getStringExtra("categoryProduct");
        String author = intent.getStringExtra("authorProduct");


        // Set dữ liệu
        Picasso.get().load(img.toString()).into(imgHinhAnhChiTietSach);
        nameProduct.setText(name);
        priceProduct.setText("  " + price + " NVD");
        descriptionProduct.setText(description);
        stockProduct.setText("Kho: " + stock);
        categoryProduct.setText("Loại sách:" + category);
        authorProduct.setText("Tác giả: " + author);
    }

    private void setAction() {

        // Khi bấm vào giỏ hàng
        imgAddCartChiTietSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        // khi bấm vào nhắn tin
        imgMessageInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MessageUserScreen.class));

            }
        });
    }

    private void setControl() {
        imgMessageInbox = findViewById(R.id.imgMessageInboxChiTietSach);
        imgAddCartChiTietSach = findViewById(R.id.imgAddCartChiTietSach);
        imgHinhAnhChiTietSach = findViewById(R.id.imgHinhAnhChiTietSach);

        nameProduct = findViewById(R.id.txtTenSachChiTietSach);
        priceProduct = findViewById(R.id.txtGiaTienChiTietSach);
        descriptionProduct = findViewById(R.id.txtMoTaChiTietSach);
        stockProduct = findViewById(R.id.txtKhoChiTietSach);
        categoryProduct = findViewById(R.id.txtTheLoaiChiTietSach);
        authorProduct = findViewById(R.id.txtTacGiaChiTietSach);
    }
}
