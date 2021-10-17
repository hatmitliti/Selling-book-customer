package com.example.book.Object;

public class ProductInCart {

    private String hinhAnh;
    private String id;
    private String tenSanPham;
    private int giaTien;
    private int numberCart;

    public ProductInCart() {
    }

    public ProductInCart(String hinhAnh, String id, String tenSanPham, int giaTien, int numberCart) {
        this.hinhAnh = hinhAnh;
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.giaTien = giaTien;
        this.numberCart = numberCart;
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
