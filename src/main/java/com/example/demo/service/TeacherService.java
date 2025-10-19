package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.TeacherDto;

public interface TeacherService {

    List<TeacherDto> getAllTeachers();

    TeacherDto getTeacherById(Long id);

    TeacherDto createTeacher(TeacherDto teacher);

    TeacherDto patchTeacher(Long id, TeacherDto teacher);

    void deleteTeacher(Long id);
}
