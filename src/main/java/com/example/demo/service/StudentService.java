package com.example.demo.service;

import com.example.demo.dto.StudentDto;

public interface StudentService {

    StudentDto getTeachersByStudentId(Long studentId);

    StudentDto getStudentBystudentId(Long id);

    StudentDto createStudent(StudentDto student);

    StudentDto updateStudent(Long id, StudentDto student);

    void DeleteStudent(Long id);

}
