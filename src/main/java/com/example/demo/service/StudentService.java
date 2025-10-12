package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StudentDto;

public interface StudentService {

    StudentDto getTeachersByStudentId(Long studentId);

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long id);

    StudentDto createStudent(StudentDto student);

    StudentDto updateStudent(Long id, StudentDto student);

    void deleteStudent(Long id);

}
