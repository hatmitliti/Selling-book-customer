package com.example.book.Object;

public class User {
    private String address;
    private String name;
    private String rank;
    private String birth;
    private String phone;
    private String image;
    private int moneyBuy;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMoneyBuy() {
        return moneyBuy;
    }

    public void setMoneyBuy(int moneyBuy) {
        this.moneyBuy = moneyBuy;
    }

    public User() {
    }

    public User(String address, String name, String rank, String birth, String phone, String image, int moneyBuy) {
        this.address = address;
        this.name = name;
        this.rank = rank;
        this.birth = birth;
        this.phone = phone;
        this.image = image;
        this.moneyBuy = moneyBuy;
    }
}
