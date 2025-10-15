package com.example.demo.service.impl;

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

    @Override
    public TeacherDto createTeacher(TeacherDto teacher) {
        Teacher teacherEntity = new Teacher(teacher);
        Teacher saveTeacher = teacherRepository.save(teacherEntity);

        // 建立book關聯
        if (teacher.getBooksId() != null && !teacher.getBooksId().isEmpty()) {

            for (Long bookId : teacher.getBooksId()) {
                Book book = bookReporsitory.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found."));

                book.setTeacher(saveTeacher);// 建立book對teacher的關聯
                saveTeacher.getBooks().add(book);// 建立teacher對book的關聯
                bookReporsitory.save(book);
            }

        }

        return new TeacherDto(saveTeacher);
    }

    @Override
    public TeacherDto updateTeacher(Long id, TeacherDto teacher) {

        Teacher teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (teacher.getName() != null) {
            teacherEntity.setName(teacher.getName());
        }

        // 更新book關聯
        if (teacher.getBooksId() != null && !teacher.getBooksId().isEmpty()) {
            Set<Long> currentIds = teacherEntity.getBooks().stream().map(Book::getId).collect(Collectors.toSet());
            Set<Long> newIds = new HashSet<>(teacher.getBooksId());// POST中關聯的Id

            if (currentIds != null && !currentIds.isEmpty()) {

                // 移除不存在的Id
                for (Long removeId : currentIds.stream().filter(bookId -> !newIds.contains(bookId))
                        .collect(Collectors.toList())) {

                    Book book = bookReporsitory.findById(removeId)
                            .orElseThrow(() -> new RuntimeException("Book not found."));
                    book.setTeacher(null); // 解除 book -> teacher
                    // 從 teacher 端集合移除該書
                    teacherEntity.getBooks().removeIf(b -> b.getId().equals(removeId));
                    bookReporsitory.save(book);
                }

                // 新增Id關聯
                for (Long addId : newIds.stream()
                        .filter(bookId -> !currentIds.contains(bookId))
                        .collect(Collectors.toList())) {

                    Book book = bookReporsitory.findById(addId)
                            .orElseThrow(() -> new RuntimeException("Book not found."));

                    book.setTeacher(teacherEntity);// 建立book對teacher的關聯
                    teacherEntity.getBooks().add(book);// 建立teacher對book的關聯
                    bookReporsitory.save(book);

                }
            } else {
                for (Long addId : newIds) {

                    Book book = bookReporsitory.findById(addId)
                            .orElseThrow(() -> new RuntimeException("Book not found."));

                    book.setTeacher(teacherEntity);// 建立book對teacher的關聯
                    teacherEntity.getBooks().add(book);// 建立teacher對book的關聯
                    bookReporsitory.save(book);

                }
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
        // getBooks().stream().map(Book::getId).collect(Collectors.toList());

        if (teacher != null) {
            List<Long> currentIds = teacher.getBooks().stream().map(Book::getId).collect(Collectors.toList());

            for (Long delId : currentIds) {
                Book book = bookReporsitory.findById(delId)
                        .orElseThrow(() -> new RuntimeException("Book not found."));
                book.setTeacher(null); // 解除 book -> teacher
                // 從 teacher 端集合移除該書
                teacher.getBooks().removeIf(b -> b.getId().equals(delId));
                bookReporsitory.save(book);
            }
            teacherRepository.deleteById(id);

        }
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(TeacherDto::new).collect(Collectors.toList());
    }

}