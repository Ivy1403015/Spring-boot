package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /*
     * @GetMapping("/student/{id}")
     * public StudentDto getStudentWithTeachers(@PathVariable Long id) {
     * return studentService.getTeachersByStudentId(id);
     * }
     */

    @GetMapping("/student/{id}")
    public StudentDto getMethodName(@PathVariable Long id) {
        return studentService.getTeachersByStudentId(id);
    }

    @PostMapping("/student")
    public StudentDto createStudent(@RequestBody StudentDto student) {
        StudentDto createStudent = studentService.createStudent(student);

        return createStudent;
    }

    @PatchMapping("/student/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody StudentDto student) {

        StudentDto updaStudent = studentService.updateStudent(id, student);

        return updaStudent;
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable Long id) {

        studentService.DeleteStudent(id);
    }

}
