package com.example.book.Object;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ProductInCart implements Serializable {

    private String hinhAnh;
    private String id;
    private String tenSanPham;
    private int giaTien;
    private int numberCart;
    private boolean chkbox;

    public ProductInCart() {
    }
    //
    public ProductInCart(String hinhAnh, String id, String tenSanPham, int giaTien, int numberCart) {
        this.hinhAnh = hinhAnh;
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.numberCart = numberCart;
        this.chkbox = false;
    }

    public boolean isChkbox() {
        return chkbox;
    }

    public void setChkbox(boolean chkbox) {
        this.chkbox = chkbox;
    }

    public int getNumberCart() {
        return numberCart;
    }

    public void setNumberCart(int numberCart) {
        this.numberCart = numberCart;
    }


    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

}
