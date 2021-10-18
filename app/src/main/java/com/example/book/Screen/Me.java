package com.example.book.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.R;
import com.google.firebase.auth.FirebaseAuth;

public class Me extends Fragment {
    private Button btnLogout;
    FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nguoi_dung, container, false);
        auth=FirebaseAuth.getInstance();
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getContext(),Login.class));
            }
        });
        return view;
    }

}
