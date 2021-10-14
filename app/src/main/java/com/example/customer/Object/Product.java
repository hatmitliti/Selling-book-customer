package com.example.customer.Object;

import android.media.Image;

public class Product {

    private int hinhAnh;
    private String tenSanPham;
    private String giaTien;

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public Product(int hinhAnh, String tenSanPham, String giaTien) {
        this.hinhAnh = hinhAnh;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
    }

    public Product() {
    }
}
