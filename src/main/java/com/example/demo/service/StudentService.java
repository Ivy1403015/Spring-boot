package com.example.demo.service;

import com.example.demo.dto.StudentDto;

public interface StudentService {

    StudentDto getTeachersByStudentId(Long studentId);

}
