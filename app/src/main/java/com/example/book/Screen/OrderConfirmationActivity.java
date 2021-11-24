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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.book.Dialog.NotificationDialog;
import com.example.book.MainActivity;
import com.example.book.Object.Bill;
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.ProductInCart;
import com.example.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class OrderConfirmationActivity extends AppCompatActivity {

    ImageView imgIconDeliver;
    TextView txtSoLuongSanPhamXacNhanDonHang,txtTongTienXacNhanDonHang,txtGiamGiaXacNhanDonHang,txtTienTraXacNhanDonHang;
    EditText edtQuanHuyenXacNhanDonHang,edtSoDienThoaiXacNhanDonHang,
            edtXaPhuongXacNhanDonHang,edtKhuPhoXacNhanDonHang,edtSoNhaTenDuongXacNhanDonHang;

    Button btnDatHangXacNhanDonHang;
    ArrayList<ProductInCart> listProduct;
    String name = "";
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        notificationDialog = new NotificationDialog(this);
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

                if (edtQuanHuyenXacNhanDonHang.getText().toString().isEmpty()) {
                    edtQuanHuyenXacNhanDonHang.setError(getResources().getString(R.string.field_empty));
                } else if (edtSoDienThoaiXacNhanDonHang.getText().toString().isEmpty()) {
                    edtSoDienThoaiXacNhanDonHang.setError(getResources().getString(R.string.field_empty));
                } else if (edtXaPhuongXacNhanDonHang.getText().toString().isEmpty()) {
                    edtXaPhuongXacNhanDonHang.setError(getResources().getString(R.string.field_empty));
                } else if (edtKhuPhoXacNhanDonHang.getText().toString().isEmpty()) {
                    edtKhuPhoXacNhanDonHang.setError(getResources().getString(R.string.field_empty));
                } else if (edtSoNhaTenDuongXacNhanDonHang.getText().toString().isEmpty()) {
                    edtSoNhaTenDuongXacNhanDonHang.setError(getResources().getString(R.string.field_empty));
                } else {

                    String address = edtSoNhaTenDuongXacNhanDonHang.getText().toString() + " - " +
                            edtKhuPhoXacNhanDonHang.getText().toString() + " - " +
                            edtXaPhuongXacNhanDonHang.getText().toString() + " - " +
                            edtQuanHuyenXacNhanDonHang.getText().toString();

                    int discount = 0;
                    if (getIntent().getStringExtra("tienGiam").equals("No voucher")) {
                        discount = 0;
                    } else {
                        String[] giam = getIntent().getStringExtra("tienGiam").split(" ");
                        discount = Integer.parseInt(giam[1]);
                    }

                    String id = UUID.randomUUID().toString();
                    String id_user = MainActivity.usernameApp;

                    String sdt = edtSoDienThoaiXacNhanDonHang.getText().toString();
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
                    notificationDialog.startSuccessfulDialog(getResources().getString(R.string.order_success));
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
        edtQuanHuyenXacNhanDonHang = findViewById(R.id.txtQuanHuyenXacNhanDonHang);
        edtXaPhuongXacNhanDonHang = findViewById(R.id.txtXaPhuongXacNhanDonHang);
        edtKhuPhoXacNhanDonHang = findViewById(R.id.txtKhuPhoXacNhanDonHang);
        edtSoNhaTenDuongXacNhanDonHang = findViewById(R.id.txtSoNhaTenDuongXacNhanDonHang);
        btnDatHangXacNhanDonHang = findViewById(R.id.btnDatHangXacNhanDonHang);
        edtSoDienThoaiXacNhanDonHang = findViewById(R.id.txtSoDienThoaiXacNhanDonHang);
    }
}
