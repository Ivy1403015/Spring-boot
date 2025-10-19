package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    List<Long> teacherIds = new ArrayList<>();

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        if (student.getTeachers() != null) {
            this.teacherIds = student.getTeachers().stream().map(Teacher::getId).collect(Collectors.toList());
        }
    }
}
