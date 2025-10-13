package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDto {

    private Long id;
    private String name;
    List<Long> teachersId = new ArrayList<>();

    public StudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        /*
         * this.teachersId = student.getTeachers().stream()
         * .map(Teacher::getId)
         * .collect(Collectors.toList());
         */
    }
}
