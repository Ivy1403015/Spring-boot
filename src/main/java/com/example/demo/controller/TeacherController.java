package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.TeacherDto;
import com.example.demo.service.TeacherService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/teacher")
    public TeacherDto addTeacher(@RequestBody Map<String, Object> request) {

        // Get teacherName
        String techerName = (String) request.get("name");
        if (techerName == null || techerName.trim().isEmpty()) {
            return new TeacherDto();
        }

        // Get StedentIds並轉為List<Long>
        List<Long> studentsId = new ArrayList<>();
        Object studentIdsObj = request.get("studentIds");
        if (studentIdsObj instanceof List) {
            List<?> rawList = (List<?>) studentIdsObj;
            for (Object id : rawList) {
                if (id instanceof Number) {
                    studentsId.add(((Number) id).longValue());
                } else {
                    return new TeacherDto();
                }
            }
        }

        // Get BooksId並轉為List<Long>
        List<Long> booksId = new ArrayList<>();
        Object bookIdsObj = request.get("bookIds");
        if (bookIdsObj instanceof List) {
            List<?> rawList = (List<?>) bookIdsObj;
            for (Object id : rawList) {
                if (id instanceof Number) {
                    booksId.add(((Number) id).longValue());
                }
            }
        }

        return teacherService.addTeacher(techerName, studentsId, booksId);
    }

    @GetMapping("/teacher/{id}")
    public TeacherDto getTeacherWithStudents(@PathVariable Long id) {
        return teacherService.getStudentsByTeacherId(id);
    }

}
