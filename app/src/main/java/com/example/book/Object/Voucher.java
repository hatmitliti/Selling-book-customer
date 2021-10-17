package com.example.book.Object;

public class Voucher {
    private String id;
    private int percent;
    private int maximum;

    public Voucher() {
    }

    public Voucher(String id, int percent, int maximum) {
        this.id = id;
        this.percent = percent;
        this.maximum = maximum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}
