package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.Adapter.CustomAdapterProduct;
import com.example.book.Adapter.CustomAdapterProductSeen;
import com.example.book.MainActivity;
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.Product;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

    GridView lvProductDetail;
    ArrayList<Product> list;
    CustomAdapterProductSeen adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chi_tiet_sach);
        list = new ArrayList<>();
        adapter = new CustomAdapterProductSeen(getApplicationContext(), R.layout.item_product_listview_seen, list);
        setControl();
        lvProductDetail.setAdapter(adapter);
        setData();
        setAction();
        getProductDetail();



        // lấy sản phẩm:
        Intent intent = getIntent();
        String img = intent.getStringExtra("imgProduct");
        String name = intent.getStringExtra("nameProduct");
        String price = intent.getStringExtra("priceProduct");
        String description = intent.getStringExtra("descriptionProduct");
        String stock = intent.getStringExtra("stockProduct");
        String category = intent.getStringExtra("categoryProduct");
        String author = intent.getStringExtra("authorProduct");
        idProduct = intent.getStringExtra("idProduct");

        Product pd = new Product(img, idProduct, name, Integer.parseInt(price), category, 0, Integer.parseInt(stock), 0, description, author, 0);

        // lưu vào ds sản phẩm đã xem:
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("product_seens");
        data.child(MainActivity.usernameApp).child(pd.getId()).setValue(pd);


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

    // lấy danh sách các sản phẩm liên quan:
    public void getProductDetail() {
        DatabaseReference dataProduct = FirebaseDatabase.getInstance().getReference("products");
        dataProduct.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Product product = snapshot.getValue(Product.class);
                if (product.getCategory().equals(getIntent().getStringExtra("categoryProduct"))) {
                    list.add(product);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // bấm vào 1 sp đó
        lvProductDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailBook.class);

                intent.putExtra("imgProduct", list.get(position).getHinhAnh());
                intent.putExtra("nameProduct", list.get(position).getTenSanPham());
                intent.putExtra("priceProduct", list.get(position).getGiaTien() + "");
                intent.putExtra("descriptionProduct", list.get(position).getDescription());
                intent.putExtra("stockProduct", list.get(position).getStock() + "");
                intent.putExtra("categoryProduct", list.get(position).getCategory());
                intent.putExtra("authorProduct", list.get(position).getAuthor());
                intent.putExtra("idProduct", list.get(position).getId());

                startActivity(intent);

            }
        });
    }


    private void setData() {
        //
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        //
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

        product = new Product(img, idProduct, name, Integer.parseInt(price), category, 0, Integer.parseInt(stock), 0, description, author, 0);

        // Set dữ liệu
        Picasso.get().load(img.toString()).into(imgHinhAnhChiTietSach);
        nameProduct.setText("Tên Sách: "+name);
        priceProduct.setText("Giá: " + en.format(Integer.parseInt(price)) + " VNĐ");
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
                if (product.getStock() == 0){
                    Toast.makeText(getApplicationContext(), "Sản phẩm đã hết", Toast.LENGTH_SHORT).show();
                }else {
                    FirebaseConnect.addProductInCart(MainActivity.usernameApp, product);
                    Toast.makeText(getApplicationContext(), "Đã thểm vào giỏ hàng !", Toast.LENGTH_SHORT).show();
                }
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
        lvProductDetail = findViewById(R.id.lvProductDetail);
    }
}
