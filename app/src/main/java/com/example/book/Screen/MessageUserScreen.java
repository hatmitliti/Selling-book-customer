package com.example.book.Screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book.Adapter.CustomAdapterMessage;
import com.example.book.Dialog.NotificationDialog;
import com.example.book.MainActivity;
import com.example.book.Object.Message;
import com.example.book.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class MessageUserScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ArrayList<Message> list = new ArrayList<>();

        ListView lvTinNhan = findViewById(R.id.lvTinNhan);
        EditText txtNoiDungTinNhan = findViewById(R.id.txtNoiDungTinNhan);
        Button btnGuiTinNhan = findViewById(R.id.btnGuiTinNhan);

// lấy tin nhắn về
        CustomAdapterMessage customAdapterMessage = new CustomAdapterMessage(getApplicationContext(), R.layout.item_nhan_tin, list);
        lvTinNhan.setAdapter(customAdapterMessage);
        DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference("messages");
        mDatabaseUser.child(MainActivity.usernameApp).addChildEventListener(new ChildEventListener() {
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
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập", Toast.LENGTH_SHORT).show();
                    NotificationDialog notificationDialog = new NotificationDialog(MessageUserScreen.this);
                    new NotificationDialog(MessageUserScreen.this).startErrorDialog("Bạn chưa nhập nội dung");

                } else {
                    Message message = new Message(content, "user");
                    DatabaseReference mDatabaseUser = FirebaseDatabase.getInstance().getReference("messages");
                    mDatabaseUser.child(MainActivity.usernameApp).child(UUID.randomUUID().toString()).setValue(message);
                    txtNoiDungTinNhan.setText("");
                }
            }
        });


    }

    private void setAction() {

    }

    private void setControl() {

    }


}
