package com.example.book.Object;

public class Message {
    private String content;
    private String who;

    public Message() {
    }

    public Message(String content, String who) {
        this.content = content;
        this.who = who;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
