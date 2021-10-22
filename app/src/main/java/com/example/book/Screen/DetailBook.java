package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book.MainActivity;
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.Product;
import com.example.book.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    String idProduct;
    Product product;

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
        idProduct = intent.getStringExtra("idProduct");

        product = new Product(img, idProduct, name, Integer.parseInt(price), category, 0, Integer.parseInt(stock), 0, description, author,0);

        // Set dữ liệu
        Picasso.get().load(img.toString()).into(imgHinhAnhChiTietSach);
        nameProduct.setText(name);
        priceProduct.setText("  " + price + " VND");
        descriptionProduct.setText(description);
        stockProduct.setText("Kho: " + stock);
        categoryProduct.setText("Loại sách:" + category);
        authorProduct.setText("Tác giả: " + author);

    }

    private void setAction() {

        // Khi bấm vào giỏ hàng thêm sản phẩm vào giỏ hàng
        imgAddCartChiTietSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseConnect.addProductInCart(MainActivity.usernameApp, product);
                Toast.makeText(getApplicationContext(), "Đã thểm vào giỏ hàng !", Toast.LENGTH_SHORT).show();
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
