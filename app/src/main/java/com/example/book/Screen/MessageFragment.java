package com.example.book.Screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterMessage;
import com.example.book.Dialog.NotificationDialog;
import com.example.book.MainActivity;
import com.example.book.Object.Message;
import com.example.book.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class MessageFragment extends Fragment {
    private NotificationDialog notificationDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_messages, container, false);
        ArrayList<Message> list = new ArrayList<>();
notificationDialog = new NotificationDialog(getActivity());
        ListView lvTinNhan = view.findViewById(R.id.lvTinNhan);
        EditText txtNoiDungTinNhan = view.findViewById(R.id.txtNoiDungTinNhan);
        Button btnGuiTinNhan = view.findViewById(R.id.btnGuiTinNhan);
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

// lấy tin nhắn về
        CustomAdapterMessage customAdapterMessage = new CustomAdapterMessage(getContext(), R.layout.item_nhan_tin, list);
        lvTinNhan.setAdapter(customAdapterMessage);
        DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference("messages");
        mDatabaseUser.child(mUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                list.add(message);
                customAdapterMessage.notifyDataSetChanged();
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
// gửi tin nhắn đi
        btnGuiTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = txtNoiDungTinNhan.getText().toString();
                if (content.equals("")) {
                //    Toast.makeText(getContext(), "Bạn chưa nhập", Toast.LENGTH_SHORT).show();

                    notificationDialog.startErrorDialog("Bạn chưa nhập nội dung");

                } else {
                    Message message = new Message(content, "user");
                    DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference("messages");
                    mDatabaseUser.child(MainActivity.usernameApp).child(UUID.randomUUID().toString()).setValue(message);
                    txtNoiDungTinNhan.setText("");
                }
            }
        });

        return view;
    }
}
