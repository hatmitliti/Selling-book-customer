package com.example.book.Screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book.MainActivity;
import com.example.book.Object.User;
import com.example.book.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUser extends AppCompatActivity {
    User user;
    int REQUEST_CODE_IMAGE = 1;
    int RESULT_LOAD_IMAGE = 2;
    String idUserCurrent;
    Context context;

    RadioButton rdbcamera, rdbThuVien;
    ImageView imgCamera, imgThuVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        CircleImageView profile_image = findViewById(R.id.profile_image);
        EditText txtNameUserEdit = findViewById(R.id.txtNameUserEdit);
        EditText txtBirthUser = findViewById(R.id.txtBirthUser);
        EditText edtDiaChi = findViewById(R.id.tvUpdateAddressUser);
        TextView txtphoneUser_ = findViewById(R.id.txtphoneUser_);
        Button btnLuu = findViewById(R.id.btnLuu);
        // Button btnBack = findViewById(R.id.backInforUser);

        /*
         * Tạo các biến để lưu file ảnh trên firebase
         * */
        //
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://selling-books-ba602.appspot.com/");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        //
        context = InfoUser.this;
        //
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");


        //lấy dữ liệu:
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("users");
        data.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(MainActivity.usernameApp)) {
                    user = snapshot.getValue(User.class);
                    try {
                        Picasso.get().load(user.getImage()).into(profile_image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    txtNameUserEdit.setText(user.getName());
                    txtBirthUser.setText(user.getBirth());
                    txtphoneUser_.setText(user.getPhone());
                    edtDiaChi.setText(user.getAddress());
                    idUserCurrent = snapshot.getKey();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                user = snapshot.getValue(User.class);
                try {
                    Picasso.get().load(user.getImage()).into(profile_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                txtNameUserEdit.setText(user.getName());
                txtBirthUser.setText(user.getBirth());
                txtphoneUser_.setText(user.getPhone());
                edtDiaChi.setText(user.getAddress());
                idUserCurrent = snapshot.getKey();
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

        //
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AAA", idUserCurrent + "");
                //
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.iteam_dialog_image, null);
                //
                rdbcamera = alertLayout.findViewById(R.id.rdbChonAnhTuCamera);
                rdbThuVien = alertLayout.findViewById(R.id.rdbChonAnhTuThuVien);
                //
                imgCamera = alertLayout.findViewById(R.id.ImageViewCameraAnhUserCanThem);
                imgThuVien = alertLayout.findViewById(R.id.ImageViewChonAnhThuVienUserCanThem);
                //
                rdbcamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imgCamera.setEnabled(true);
                        imgThuVien.setEnabled(false);
                        imgCamera.setBackground(null);
                        imgThuVien.setBackgroundResource(R.drawable.backgroundimage);
                    }
                });
                rdbThuVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imgCamera.setEnabled(false);
                        imgThuVien.setEnabled(true);
                        imgThuVien.setBackground(null);
                        imgCamera.setBackgroundResource(R.drawable.backgroundimage);
                    }
                });
                //
                imgCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_IMAGE);
                        Toast.makeText(context, "Lấy Ảnh Từ Camera", Toast.LENGTH_SHORT).show();
                    }
                });
                imgThuVien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", true);
                        intent.putExtra("outputX", 256);
                        intent.putExtra("outputY", 256);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE);

                        Toast.makeText(context, "Lấy Ảnh Từ Thư Viện", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Chọn ảnh đại diện ");
                builder.setView(alertLayout);
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (rdbcamera.isChecked()) {
                            if (!user.getImage().equals("") && !user.getNameImage().equals("")) {
                                Calendar calendar = Calendar.getInstance();
                                String imageName = "image" + calendar.getTimeInMillis() + ".png";
                                // Create a reference to "mountains.jpg"
                                StorageReference mountainsRef = storageRef.child("ImagesUsers/" + imageName);
                                // Get the data from an ImageView as bytes
                                imgCamera.setDrawingCacheEnabled(true);
                                imgCamera.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imgCamera.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...

                                        if (taskSnapshot.getMetadata() != null) {
                                            if (taskSnapshot.getMetadata().getReference() != null) {
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageURL = uri.toString();
                                                        //tạo đối tượng Product và thêm đối tượng vào firsebase
                                                        User user1 = new User(user.getAddress(), user.getBirth(), user.getMoneyBuy(), user.getName(), user.getPhone(), user.getRank(), imageURL, imageName);
                                                        StorageReference desertRef = storageRef.child("ImagesUsers/" + user.getNameImage());
                                                        desertRef.delete();
                                                        mDatabase.child(idUserCurrent).setValue(user1).addOnSuccessListener(new OnSuccessListener() {
                                                            @Override
                                                            public void onSuccess(Object o) {
                                                                Picasso.get().load(imageURL).into(profile_image);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            } else {
                                Calendar calendar = Calendar.getInstance();
                                String imageName = "image" + calendar.getTimeInMillis() + ".png";
                                // Create a reference to "mountains.jpg"
                                StorageReference mountainsRef = storageRef.child("ImagesUsers/" + imageName);
                                // Get the data from an ImageView as bytes
                                imgCamera.setDrawingCacheEnabled(true);
                                imgCamera.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imgCamera.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...

                                        if (taskSnapshot.getMetadata() != null) {
                                            if (taskSnapshot.getMetadata().getReference() != null) {
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageURL = uri.toString();
                                                        //tạo đối tượng Product và thêm đối tượng vào firsebase
                                                        User user1 = new User(user.getAddress(), user.getBirth(), user.getMoneyBuy(), user.getName(), user.getPhone(), user.getRank(), imageURL, imageName);
                                                        mDatabase.child(idUserCurrent).setValue(user1).addOnSuccessListener(new OnSuccessListener() {
                                                            @Override
                                                            public void onSuccess(Object o) {
                                                                Picasso.get().load(imageURL).into(profile_image);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }

                        } else if (rdbThuVien.isChecked()) {
                            if (!user.getImage().equals("") && !user.getNameImage().equals("")) {
                                Calendar calendar = Calendar.getInstance();
                                String imageName = "image" + calendar.getTimeInMillis() + ".png";
                                // Create a reference to "mountains.jpg"
                                StorageReference mountainsRef = storageRef.child("ImagesUsers/" + imageName);
                                // Get the data from an ImageView as bytes
                                imgThuVien.setDrawingCacheEnabled(true);
                                imgThuVien.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imgThuVien.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...

                                        if (taskSnapshot.getMetadata() != null) {
                                            if (taskSnapshot.getMetadata().getReference() != null) {
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageURL = uri.toString();
                                                        //tạo đối tượng Product và thêm đối tượng vào firsebase
                                                        User user1 = new User(user.getAddress(), user.getBirth(), user.getMoneyBuy(), user.getName(), user.getPhone(), user.getRank(), imageURL, imageName);
                                                        StorageReference desertRef = storageRef.child("ImagesUsers/" + user.getNameImage());
                                                        desertRef.delete();
                                                        mDatabase.child(idUserCurrent).setValue(user1).addOnSuccessListener(new OnSuccessListener() {
                                                            @Override
                                                            public void onSuccess(Object o) {
                                                                Picasso.get().load(imageURL).into(profile_image);
                                                                Toast.makeText(context, "Sửa Ảnh Thành Công", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            } else {
                                Calendar calendar = Calendar.getInstance();
                                String imageName = "image" + calendar.getTimeInMillis() + ".png";
                                // Create a reference to "mountains.jpg"
                                StorageReference mountainsRef = storageRef.child("ImagesUsers/" + imageName);
                                // Get the data from an ImageView as bytes
                                imgThuVien.setDrawingCacheEnabled(true);
                                imgThuVien.buildDrawingCache();
                                Bitmap bitmap = ((BitmapDrawable) imgThuVien.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                byte[] data = baos.toByteArray();

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(context, "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...

                                        if (taskSnapshot.getMetadata() != null) {
                                            if (taskSnapshot.getMetadata().getReference() != null) {
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageURL = uri.toString();
                                                        //tạo đối tượng Product và thêm đối tượng vào firsebase
                                                        User user1 = new User(user.getAddress(), user.getBirth(), user.getMoneyBuy(), user.getName(), user.getPhone(), user.getRank(), imageURL, imageName);
                                                        mDatabase.child(idUserCurrent).setValue(user1).addOnSuccessListener(new OnSuccessListener() {
                                                            @Override
                                                            public void onSuccess(Object o) {
                                                                Picasso.get().load(imageURL).into(profile_image);
                                                                Toast.makeText(context, "Sửa Ảnh Thành Công", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }

                        }
                    }
                });
                builder.setNegativeButton("Không", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNameUserEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (txtBirthUser.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (txtphoneUser_.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(edtDiaChi.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Không được để trống dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    user.setBirth(txtBirthUser.getText().toString());
                    user.setName(txtNameUserEdit.getText().toString());
                    user.setPhone(txtphoneUser_.getText().toString());
                    user.setAddress(edtDiaChi.getText().toString());

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(MainActivity.usernameApp).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                }

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
    /*
         Gọi Hàm Đổ Hình chụp từ camera ra màn hình
         */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgCamera.setImageBitmap(bitmap);
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imgThuVien.setImageURI(imageUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}