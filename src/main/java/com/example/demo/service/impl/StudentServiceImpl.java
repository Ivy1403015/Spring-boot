package com.example.demo.service.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDto getTeachersByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student != null) {
            Hibernate.initialize(student.getTeachers());
        }

        return student != null ? new StudentDto(student) : null;
    }

}
