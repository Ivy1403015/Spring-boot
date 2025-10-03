package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    public TeacherDto(Teacher teacher) {
        this.teacherId = teacher.getId();
        this.name = teacher.getName();
        /*
         * this.studentsId =
         * teacher.getStudents().stream().map(Student::getId).collect(Collectors.toList(
         * ));
         * this.booksId =
         * teacher.getBooks().stream().map(Book::getId).collect(Collectors.toList());
         */
    }

    public TeacherDto() {

    }

    private Long teacherId;
    private String name;

    private List<Long> booksId = new ArrayList<>();
    private List<Long> studentsId = new ArrayList<>();

}
