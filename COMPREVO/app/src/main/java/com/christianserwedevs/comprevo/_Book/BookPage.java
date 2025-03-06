package com.christianserwedevs.comprevo._Book;

public class BookPage {
    private String text;
    private int imageResId;

    public BookPage(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
