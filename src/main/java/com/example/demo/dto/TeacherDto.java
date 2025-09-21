package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto {

    public TeacherDto(Teacher teacher) {
        this.teacherId = teacher.getId();
        this.name = teacher.getName();
        this.studentsId = teacher.getStudents().stream().map(Student::getId).collect(Collectors.toList());
        this.books = teacher.getBooks().stream().map(BookDto::new).collect(Collectors.toList());
    }

    public TeacherDto() {
        this.books = new ArrayList<>();
        this.studentsId = new ArrayList<>();
    }

    @JsonProperty("teacherId")
    private Long teacherId;
    private String name;

    private List<BookDto> books;
    private List<Long> studentsId;

}
