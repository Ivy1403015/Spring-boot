package com.example.demo.dto;

import com.example.demo.entity.Book;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    public BookDto(Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    public BookDto() {
    }

    private Long bookId;
    private String title;
    private String author;

    private Teacher teacher;

}
