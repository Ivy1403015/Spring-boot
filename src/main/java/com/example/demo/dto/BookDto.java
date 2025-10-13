package com.example.demo.dto;

import com.example.demo.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String name;
    private Long hoursesId;
    private Long teacherId;

    public BookDto(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        if (book.getHourses() != null) {
            this.hoursesId = book.getHourses().getId();
        }

        if (book.getTeacher() != null) {
            this.teacherId = book.getTeacher().getId();
        }
    }

}
