package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TeacherDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.BookReporsitory;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.TeacherService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final BookReporsitory bookReporsitory;

    @Override
    public TeacherDto getTeacherById(Long id) {
        Teacher getTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        return new TeacherDto(getTeacher);
    }

    @Transactional
    @Override
    public TeacherDto createTeacher(TeacherDto teacher) {
        Teacher teacherEntity = new Teacher(teacher);

        // 建立book關聯
        if (teacher.getBookIds() != null && !teacher.getBookIds().isEmpty()) {

            for (Long bookId : teacher.getBookIds()) {
                Book book = bookReporsitory.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

                book.setTeacher(teacherEntity);// 建立book對teacher的關聯
                teacherEntity.getBooks().add(book);// 建立teacher對book的關聯
                bookReporsitory.save(book);
            }

        }

        if (teacher.getStudentIds() != null && !teacher.getStudentIds().isEmpty()) {

            List<Student> students = studentRepository.findAllById(teacher.getStudentIds()); // 取得POST中的所有student資料
            teacherEntity.setStudents(students); // 建立teacher對student的關聯

        }

        Teacher saveTeacher = teacherRepository.save(teacherEntity);
        return new TeacherDto(saveTeacher);
    }

    @Transactional
    @Override
    public TeacherDto patchTeacher(Long id, TeacherDto teacher) {

        Teacher teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (teacher.getName() != null) {
            teacherEntity.setName(teacher.getName());
        }
        // 更新book關聯
        if (teacher.getBookIds() != null) {
            // 1. 解除舊的關聯 - 將原本關聯的book設為null
            for (Book oldBook : teacherEntity.getBooks())
                oldBook.setTeacher(null);
            teacherEntity.getBooks().clear();
            // 2. 建立新的關聯
            if (!teacher.getBookIds().isEmpty()) {
                List<Book> newBooks = bookReporsitory.findAllById(teacher.getBookIds());
                for (Book book : newBooks) {
                    book.setTeacher(teacherEntity); // 設定book -> teacher
                    teacherEntity.getBooks().add(book); // 設定teacher -> book
                }
            }
        }

        if (teacher.getStudentIds() != null) {

            // 清除所有關聯
            if (teacher.getStudentIds().isEmpty()) {
                teacherEntity.setStudents(new ArrayList<>());
            } else {
                List<Student> students = studentRepository.findAllById(teacher.getStudentIds());
                teacherEntity.setStudents(students);
            }
        }
        Teacher saveTeacher = teacherRepository.save(teacherEntity);

        return new TeacherDto(saveTeacher);
    }

    @Transactional
    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        for (Book book : teacher.getBooks()) {
            book.setTeacher(null); // 解除 book -> teacher
        }

        teacher.getStudents().clear(); // 刪除teacher對student關聯
        teacherRepository.delete(teacher);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}