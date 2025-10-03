package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.StudentDto;
import com.example.demo.dto.TeacherDto;

public interface TeacherService {

    TeacherDto getStudentsByTeacherId(Long teacherId);

    TeacherDto getBooksByTeacherId(Long teacherId);

    TeacherDto addTeacher(String teacherName, List<Long> studentsId, List<Long> bookIds);

    List<TeacherDto> getAllTeachers();

    TeacherDto getTeacherByteacherID(Long id);

    TeacherDto createTeacher(TeacherDto teacher);

    TeacherDto updateTeacher(Long id, TeacherDto teacher);

    void deleteTeacher(Long id);
}
