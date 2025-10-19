package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.StudentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public StudentDto getTeachersByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if (student != null) {
            Hibernate.initialize(student.getTeachers());
        }

        return student != null ? new StudentDto(student) : null;
    }

    @Override
    public StudentDto getStudentById(Long id) {

        Student getStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return new StudentDto(getStudent);
    }

    @Override
    @Transactional
    public StudentDto createStudent(StudentDto student) {

        Student studentEntity = new Student(student);

        if (student.getTeacherIds() != null) {

            List<Teacher> teachers = teacherRepository.findAllById(student.getTeacherIds());

            for (Teacher teacher : teachers) {
                teacher.getStudents().add(studentEntity); // 設定teacher對student的關聯
            }
            studentEntity.setTeachers(teachers);

            teacherRepository.saveAll(teachers);// 確保teacher同步至關聯表

        }
        Student saveStudent = studentRepository.save(studentEntity);

        return new StudentDto(saveStudent);

    }

    @Override
    public StudentDto patchStudent(Long id, StudentDto student) {

        Student studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentEntity.setName(student.getName());

        if (student.getTeacherIds() != null) {

            // 清除teacher對student的關聯
            for (Teacher teacher : studentEntity.getTeachers()) {
                teacher.getStudents().remove(studentEntity);
            }

            if (student.getTeacherIds().isEmpty()) {

                // 清除Student對teacher的關聯
                studentEntity.getTeachers().clear();
            } else {

                List<Teacher> teachers = teacherRepository.findAllById(student.getTeacherIds());

                for (Teacher teacher : teachers) {
                    teacher.getStudents().add(studentEntity);
                }
                studentEntity.setTeachers(teachers);

            }

        }

        Student saveStudent = studentRepository.save(studentEntity);

        return new StudentDto(saveStudent);
    }

    @Transactional
    @Override
    public void deleteStudent(Long id) {
        Student studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        for (Teacher teacher : studentEntity.getTeachers()) {
            teacher.getStudents().remove(studentEntity);
        }
        studentEntity.setTeachers(null);

        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

}
