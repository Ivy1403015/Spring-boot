package com.example.demo.dto;

import com.example.demo.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    public BookDto() {
    }

    private Long id;
    private String title;
    private String author;

}
