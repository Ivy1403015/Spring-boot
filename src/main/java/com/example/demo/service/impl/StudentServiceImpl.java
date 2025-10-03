package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public StudentDto getStudentBystudentId(Long id) {
        Student getStudent = studentRepository.findById(id).get();

        return new StudentDto(getStudent);
    }

    @Override
    public StudentDto createStudent(StudentDto student) {

        Student studentEntity = new Student(student);
        Student saveStudent = studentRepository.save(studentEntity);

        return new StudentDto(saveStudent);

    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto student) {
        Student studentEntity = studentRepository.findById(id).get();
        studentEntity.setName(student.getName());
        Student saveStudent = studentRepository.save(studentEntity);

        return new StudentDto(saveStudent);
    }

    @Override
    public void DeleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

}
