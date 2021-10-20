package com.example.book.Object;

public class Voucher {

    private String id;
    private int maximum;

    public Voucher() {
    }

    public Voucher(String id,  int maximum) {
        this.id = id;
        this.maximum = maximum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}
