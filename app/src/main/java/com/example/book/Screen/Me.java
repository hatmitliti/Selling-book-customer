package com.example.book.Screen;

import static android.app.Activity.RESULT_OK;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book.Adapter.CustomAdapterProduct;
import com.example.book.MainActivity;
import com.example.book.Object.Product;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Me extends Fragment {
    TextView txtPhoneUser;
    TextView txtAddressUser;
    TextView txtRankUser;
    TextView txtNameUser;
    Button btnTrangThaiDonHangUser;
    Button btnInfo;
    Button btnDoiMatKhau;
    User user;
    ImageView imgUser;
    int REQUEST_CODE_IMAGE = 1;
    int RESULT_LOAD_IMAGE = 2;
    String idUserCurrent;
    Context context;

    GridView gvSpDaXem;
    ArrayList<Product> list;
    CustomAdapterProduct adapter;
    RadioButton rdbcamera, rdbThuVien;
    ImageView imgCamera, imgThuVien;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nguoi_dung, container, false);
        /*
         * Tạo các biến để lưu file ảnh trên firebase
         * */
        //
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://selling-books-ba602.appspot.com/");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        //
        context = view.getContext();


        // set control
        btnTrangThaiDonHangUser = view.findViewById(R.id.btnTrangThaiDonHangUser);
        txtAddressUser = view.findViewById(R.id.txtAddressUser);
        txtPhoneUser = view.findViewById(R.id.txtPhoneUser);
        txtRankUser = view.findViewById(R.id.txtRankUser);
        txtNameUser = view.findViewById(R.id.txtNameUser);
        imgUser = view.findViewById(R.id.imgUser);
        gvSpDaXem = view.findViewById(R.id.gvSpDaXem);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnInfo = view.findViewById(R.id.btnInfo);


        // lấy thông tin user:
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(MainActivity.usernameApp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                txtNameUser.setText(user.getName());
                txtPhoneUser.setText("Số điện thoại: " + user.getPhone());
                txtAddressUser.setText("Địa chỉ: " + user.getAddress());
                txtRankUser.setText("Hạng thành viên: " + user.getRank());
                idUserCurrent = snapshot.getKey();
                if (user.getImage().equals("")) {

                } else {
                    Picasso.get().load(user.getImage()).into(imgUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // set action
        btnTrangThaiDonHangUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderStatus.class));
            }
        });


        list = new ArrayList<>();
        adapter = new CustomAdapterProduct(getContext(), R.layout.item_product_listview, list);
        gvSpDaXem.setAdapter(adapter);

        // lấy ds các sản phẩm đã xem:
        DatabaseReference data = FirebaseDatabase.getInstance().getReference("product_seens");
        data.child(MainActivity.usernameApp).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                list.add(snapshot.getValue(Product.class));
                adapter.notifyDataSetChanged();
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


        // Bấm vào ds đã xem đi đến trang chi tiết :
        gvSpDaXem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailBook.class);

                intent.putExtra("imgProduct", list.get(position).getHinhAnh());
                intent.putExtra("idProduct", list.get(position).getId());
                intent.putExtra("nameProduct", list.get(position).getTenSanPham());
                intent.putExtra("priceProduct", list.get(position).getGiaTien() + "");
                intent.putExtra("descriptionProduct", list.get(position).getDescription());
                intent.putExtra("stockProduct", list.get(position).getStock() + "");
                intent.putExtra("categoryProduct", list.get(position).getCategory());
                intent.putExtra("authorProduct", list.get(position).getAuthor());

                startActivity(intent);
            }
        });


        // Bấm vào ảnh đại diện user hiển thị dialog để chụp hình hoặc láy ảnh từ thư viện
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        Toast.makeText(getContext(), "Lấy Ảnh Từ Camera", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(getContext(), "Lấy Ảnh Từ Thư Viện", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Chọn ảnh đại diện ");
                builder.setView(alertLayout);
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        if (rdbcamera.isChecked()) {
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
                                    Toast.makeText(getContext(), "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
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
                                                            Picasso.get().load(imageURL).into(imgUser);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        } else if (rdbThuVien.isChecked()) {
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
                                    Toast.makeText(getContext(), "Đã Xảy Ra Lỗi Không Thể Sửa Ảnh", Toast.LENGTH_SHORT).show();
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
                                                            Picasso.get().load(imageURL).into(imgUser);
                                                            Toast.makeText(getContext(), "Sửa Ảnh Thành Công", Toast.LENGTH_SHORT).show();
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
                });
                builder.setNegativeButton("Không", null);

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // bấm vào nút đổi mật khẩu:
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });

        // bấm vào nút cập nhật thông tin user
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InfoUser.class));
            }
        });


        return view;
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
