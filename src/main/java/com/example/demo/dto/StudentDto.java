package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        /*
         * this.teachersId = student.getTeachers().stream()
         * .map(Teacher::getId)
         * .collect(Collectors.toList());
         */
    }

    public StudentDto() {

    }

    private Long id;
    private String name;
    List<Long> teachersId;

}
