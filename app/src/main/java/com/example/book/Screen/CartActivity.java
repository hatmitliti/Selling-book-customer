package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterProductInCart;
import com.example.book.Dialog.NotificationDialog;
import com.example.book.Object.FirebaseConnect;
import com.example.book.Object.ProductInCart;
import com.example.book.Object.Voucher;
import com.example.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartActivity extends Fragment {
    GridView lvProductInCart;
    TextView txtTongTienInCart,txtTienGiamInCart,txtTienTraInCart;
    CheckBox chkTatCaInCart;
    Button btnMuaHangInCart;
    CustomAdapterProductInCart adapterProductInCart;
    ArrayList<ProductInCart> listProductInCart;
    DatabaseReference dataProduct;
    Spinner spinnerVoucherInCart;
    FirebaseUser mUser;
    private NotificationDialog notificationDialog;
   // ArrayList<String> mKey = new ArrayList<>();
    ArrayAdapter adapter;
    int tongTien;
    int giamTien;
    int traTien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cart, container, false);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        notificationDialog = new NotificationDialog(getActivity());
        setControl(view);
        setAction();
        setTotalMoney();
        checkTickTatCa();
        clickButtonMuaHang();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataInDatabase();
    }

    private void clickButtonMuaHang() {
        btnMuaHangInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tongTien == 0) {
                    notificationDialog.startErrorDialog(getResources().getString(R.string.no_products));
                } else {
                    ArrayList<ProductInCart> list = new ArrayList<>();
                    for (int j = 0; j < listProductInCart.size(); j++) {
                        if (listProductInCart.get(j).isChkbox()) {
                            list.add(listProductInCart.get(j));
                        }
                    }
                    // truyền dữ liệu qua màn hình xác nhận mua
                    Intent intent = new Intent(getActivity(), OrderConfirmationActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("listProductCart", (Serializable) list);
                    intent.putExtras(b);

                    intent.putExtra("tongTien", tongTien + "");
                    intent.putExtra("tienGiam", spinnerVoucherInCart.getSelectedItem().toString());
                    intent.putExtra("tienTra", traTien + "");
                    startActivity(intent);
                }
            }
        });
    }

    private void checkTickTatCa() {
        chkTatCaInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkTatCaInCart.isChecked() == true) {
                    for (int j = 0; j < listProductInCart.size(); j++) {
                        listProductInCart.get(j).setChkbox(true);
                        FirebaseConnect.setCheckedProductInCart(listProductInCart.get(j));
                    }
                } else {
                    for (int j = 0; j < listProductInCart.size(); j++) {
                        listProductInCart.get(j).setChkbox(false);
                        FirebaseConnect.setCheckedProductInCart(listProductInCart.get(j));
                    }
                }
            }
        });
    }

    private void setAction() {
        // lấy danh sách sản phẩm trong cart
        listProductInCart = new ArrayList<>();
        getDataInDatabase();
        adapterProductInCart = new CustomAdapterProductInCart(getContext(), R.layout.item_product_in_cart, listProductInCart);
        lvProductInCart.setAdapter(adapterProductInCart);
        // khi chọn voucher:
        ArrayList<String> listVoucher = new ArrayList<>();
        listVoucher.add("No voucher");
        getDataVoucher(listVoucher);
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listVoucher);
        spinnerVoucherInCart.setAdapter(adapter);
        spinnerVoucherInCart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setTotalMoney();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getDataVoucher(ArrayList<String> listVoucher) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("vouchers");
        database.child(mUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Voucher voucher = snapshot.getValue(Voucher.class);
                voucher.setId(snapshot.getKey());
                listVoucher.add("Giảm: " + voucher.getMaximum() + " " + "VND" + " - ID: " + voucher.getId());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String id = snapshot.getKey();
                for (int j = 0; j < listVoucher.size(); j++) {
                    if (!listVoucher.get(j).equals("No voucher")) {
                        if (id == listVoucher.get(j).split(" ")[5]) {
                            listVoucher.remove(j);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setTotalMoney() {
        int total = 0;
        for (int j = 0; j < listProductInCart.size(); j++) {
            if (listProductInCart.get(j).isChkbox() == true) {
                total += listProductInCart.get(j).getGiaTien() * listProductInCart.get(j).getNumberCart();
            }
        }
        if (total == 0) {
            spinnerVoucherInCart.setSelection(0);
            txtTongTienInCart.setText("0");
            txtTienGiamInCart.setText("0");
            txtTienTraInCart.setText("0");
        } else {
            int giam = 0;
            if (!spinnerVoucherInCart.getSelectedItem().toString().equals("No voucher")) {
                String[] arrGiamTien = spinnerVoucherInCart.getSelectedItem().toString().split(" ");
                giam = Integer.parseInt(arrGiamTien[1]);
            }
            txtTongTienInCart.setText(NumberFormat.getInstance().format(total));
            txtTienGiamInCart.setText(NumberFormat.getInstance().format(giam));
            txtTienTraInCart.setText(NumberFormat.getInstance().format((total - giam) < 0 ? 0 : (total - giam)));
        }
        tongTien = total;
        if (spinnerVoucherInCart.getSelectedItem().toString().equals("No voucher")) {
            giamTien = 0;
        } else {
            String[] arrGiamTien = spinnerVoucherInCart.getSelectedItem().toString().split(" ");
            giamTien = Integer.parseInt(arrGiamTien[1]);
        }

        traTien = (tongTien - giamTien);
        if (traTien < 0) {
            traTien = 0;
        }

    }

    public void getDataInDatabase() {
        dataProduct.child("carts").child(mUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductInCart productInCart = snapshot.getValue(ProductInCart.class);
                boolean check = false;
                for (int i = 0; i < listProductInCart.size(); i++) {
                    if (listProductInCart.get(i).getId().equals(productInCart.getId())) {
                        check = true;
                    }
                }
                if (check == false) {
                    listProductInCart.add(productInCart);
                //    mKey.add(snapshot.getKey());
                    adapterProductInCart.notifyDataSetChanged();
                }


                setTotalMoney();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductInCart product = snapshot.getValue(ProductInCart.class);
                for (int i=0;i<listProductInCart.size();i++){
                    if (listProductInCart.get(i).getId().equals(product.getId())){
                        listProductInCart.set(i, product);
                    }
                }
                adapterProductInCart.notifyDataSetChanged();
                setTotalMoney();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ProductInCart product = snapshot.getValue(ProductInCart.class);
                for (int i=0;i<listProductInCart.size();i++){
                    if (listProductInCart.get(i).getId().equals(product.getId())){
                        listProductInCart.remove(i);
                    }
                }
                adapterProductInCart.notifyDataSetChanged();
                setTotalMoney();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setControl(View view) {
        lvProductInCart = view.findViewById(R.id.lvProductInCart);
        txtTongTienInCart = view.findViewById(R.id.txtTongTienInCart);
        chkTatCaInCart = view.findViewById(R.id.chkTatCaInCart);
        btnMuaHangInCart = view.findViewById(R.id.btnMuaHangInCart);
        dataProduct = FirebaseDatabase.getInstance().getReference();
        spinnerVoucherInCart = view.findViewById(R.id.spinnerVoucherInCart);
        txtTienGiamInCart = view.findViewById(R.id.txtTienGiamInCart);
        txtTienTraInCart = view.findViewById(R.id.txtTienTraInCart);
    }

}
