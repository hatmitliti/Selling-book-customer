package com.example.book.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book.MainActivity;
import com.example.book.Object.Evalute;
import com.example.book.Object.FirebaseConnect;
import com.example.book.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CustomAdapterEvalute extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<FirebaseConnect.ProductCart> list;
    ArrayList<String> idListProduct;

    public CustomAdapterEvalute(Context context, int resource, ArrayList<FirebaseConnect.ProductCart> list, ArrayList<String> idListProduct) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        this.idListProduct = idListProduct;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        String id = idListProduct.get(position);
        TextView txtTenSachInDanhGia = convertView.findViewById(R.id.txtTenSachInDanhGia);
        Spinner txtSoSaoInDanhGia = convertView.findViewById(R.id.txtSoSaoInDanhGia);
        ImageView btnOk = convertView.findViewById(R.id.btnOk);

        FirebaseConnect.ProductCart productCart = list.get(position);
        txtTenSachInDanhGia.setText(productCart.getName());
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("evalute");
                int start = Integer.parseInt(txtSoSaoInDanhGia.getSelectedItem().toString());

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();


                Evalute evalute = new Evalute(UUID.randomUUID().toString(),id, MainActivity.usernameApp,start,dateFormat.format(date));
                mDatabase.child(evalute.getId()).setValue(evalute);
                FirebaseConnect.setStartProduct(evalute);
                Drawable d = context.getResources().getDrawable(R.drawable.ok_black);
                btnOk.setBackground(d);
                btnOk.setEnabled(false);
            }
        });

        return convertView;
    }
}
