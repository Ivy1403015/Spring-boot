package com.example.demo.dto;

import com.example.demo.entity.Book;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
    }

    public BookDto() {
    }

    private Long id;
    private String name;

    private Teacher teacher;

}
