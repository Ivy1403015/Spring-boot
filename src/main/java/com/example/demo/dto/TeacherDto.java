package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDto {

    private Long id;
    private String name;

    private List<Long> booksId = new ArrayList<>();
    private List<Long> studentsId = new ArrayList<>();

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();

        if (teacher.getStudents() != null) {
            this.studentsId = teacher.getStudents().stream().map(Student::getId).collect(Collectors.toList());
        }

        if (teacher.getBooks() != null) {
            this.booksId = teacher.getBooks().stream().map(Book::getId).collect(Collectors.toList());
        }

    }
}
