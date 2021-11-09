package com.example.book.Object;

public class Bill {
    private String id;
    private String id_user;
    private String name;
    private String address;
    private String phone;
    private String shipper;
    private String date;
    private int discount;
    private int status;
    private int totalMoney;
    private boolean evalute;

    public Bill() {
    }

    public Bill(String id, String id_user, String name, String address, String phone, String shipper, String date, int discount, int status, int totalMoney, boolean evalute) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.shipper = shipper;
        this.date = date;
        this.discount = discount;
        this.status = status;
        this.totalMoney = totalMoney;
        this.evalute = evalute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public boolean isEvalute() {
        return evalute;
    }

    public void setEvalute(boolean evalute) {
        this.evalute = evalute;
    }
}
