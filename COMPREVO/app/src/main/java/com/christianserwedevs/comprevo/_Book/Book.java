package com.christianserwedevs.comprevo._Book;

import java.util.List;

public class Book {
    private String title;
    private String author;
    private List<BookPage> pages;

    public Book(String title, String author, List<BookPage> pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }



    public List<BookPage> getPages() {
        return pages;
    }
}
