package com.example.book.Screen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.MainActivity;
import com.example.book.Object.Bill;
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.ProductInCart;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class OrderConfirmation extends AppCompatActivity {

    ImageView imgIconDeliver;
    TextView txtSoLuongSanPhamXacNhanDonHang;
    TextView txtTongTienXacNhanDonHang;
    TextView txtGiamGiaXacNhanDonHang;
    TextView txtTienTraXacNhanDonHang;

    EditText txtQuanHuyenXacNhanDonHang;
    EditText txtSoDienThoaiXacNhanDonHang;
    EditText txtXaPhuongXacNhanDonHang;
    EditText txtKhuPhoXacNhanDonHang;
    EditText txtSoNhaTenDuongXacNhanDonHang;

    Button btnDatHangXacNhanDonHang;
    ArrayList<ProductInCart> listProduct;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xac_nhan_dat_hang);
        setControl();
        setText();
        setAction();
    }

    private void setText() {
        imgIconDeliver.setImageResource(R.drawable.don_hang);
        Bundle b = getIntent().getExtras();
        listProduct = (ArrayList<ProductInCart>) b.getSerializable("listProductCart");

        Intent intent = getIntent();
        txtSoLuongSanPhamXacNhanDonHang.setText("Tổng số lượng sản phẩm: " + listProduct.size() + "");
        txtTongTienXacNhanDonHang.setText("Tổng tiền: " + NumberFormat.getInstance().format(Integer.parseInt(intent.getStringExtra("tongTien"))));


        if (getIntent().getStringExtra("tienGiam").equals("No voucher")) {
            txtGiamGiaXacNhanDonHang.setText("Tiền giảm: " + 0);
        } else {
            String[] giam = getIntent().getStringExtra("tienGiam").split(" ");
            txtGiamGiaXacNhanDonHang.setText("Tiền giảm: " + NumberFormat.getInstance().format(Integer.parseInt(giam[1])));
        }
        txtTienTraXacNhanDonHang.setText("Tiền trả: " + NumberFormat.getInstance().format(Integer.parseInt(intent.getStringExtra("tienTra"))));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(MainActivity.usernameApp).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void setAction() {
        // Bấm đặt hàng
        btnDatHangXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if (txtQuanHuyenXacNhanDonHang.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else if (txtSoDienThoaiXacNhanDonHang.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else if (txtXaPhuongXacNhanDonHang.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else if (txtKhuPhoXacNhanDonHang.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else if (txtSoNhaTenDuongXacNhanDonHang.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else {

                    String address = txtSoNhaTenDuongXacNhanDonHang.getText().toString() + " " +
                            txtKhuPhoXacNhanDonHang.getText().toString() + " " +
                            txtXaPhuongXacNhanDonHang.getText().toString() + " " +
                            txtQuanHuyenXacNhanDonHang.getText().toString();

                    int discount = 0;
                    if (getIntent().getStringExtra("tienGiam").equals("No voucher")) {
                        discount = 0;
                    } else {
                        String[] giam = getIntent().getStringExtra("tienGiam").split(" ");
                        discount = Integer.parseInt(giam[1]);
                    }

                    String id = UUID.randomUUID().toString();
                    String id_user = MainActivity.usernameApp;

                    String sdt = txtSoDienThoaiXacNhanDonHang.getText().toString();
                    int tong = Integer.parseInt(getIntent().getStringExtra("tongTien"));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();

                    Bill bill = new Bill(address, discount, id, id_user, name, 0, tong, sdt, "", dateFormat.format(date), false);
                    FirebaseConnect.createBill(bill, listProduct);

                    if (!getIntent().getStringExtra("tienGiam").equals("No voucher")) {
                        String id_voucher = getIntent().getStringExtra("tienGiam").split(" ")[5];
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("vouchers");

                        mDatabase.child(MainActivity.usernameApp).child(id_voucher).removeValue();
                    }
                    Toast.makeText(getApplicationContext(), "Đơn hàng đã được đặt", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }


        });
    }

    private void setControl() {
        imgIconDeliver = findViewById(R.id.imgIconDeliver);
        txtSoLuongSanPhamXacNhanDonHang = findViewById(R.id.txtSoLuongSanPhamXacNhanDonHang);
        txtTongTienXacNhanDonHang = findViewById(R.id.txtTongTienXacNhanDonHang);
        txtGiamGiaXacNhanDonHang = findViewById(R.id.txtGiamGiaXacNhanDonHang);
        txtTienTraXacNhanDonHang = findViewById(R.id.txtTienTraXacNhanDonHang);
        txtQuanHuyenXacNhanDonHang = findViewById(R.id.txtQuanHuyenXacNhanDonHang);
        txtXaPhuongXacNhanDonHang = findViewById(R.id.txtXaPhuongXacNhanDonHang);
        txtKhuPhoXacNhanDonHang = findViewById(R.id.txtKhuPhoXacNhanDonHang);
        txtSoNhaTenDuongXacNhanDonHang = findViewById(R.id.txtSoNhaTenDuongXacNhanDonHang);
        btnDatHangXacNhanDonHang = findViewById(R.id.btnDatHangXacNhanDonHang);
        txtSoDienThoaiXacNhanDonHang = findViewById(R.id.txtSoDienThoaiXacNhanDonHang);
    }
}
