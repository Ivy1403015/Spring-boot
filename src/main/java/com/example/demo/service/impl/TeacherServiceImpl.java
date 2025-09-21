package com.example.demo.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TeacherDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Student;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.BookReporsitory;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.TeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final BookReporsitory bookReporsitory;

    @Override
    public TeacherDto getStudentsByTeacherId(Long teacherId) {

        // 先搜尋老師資料，再依關聯取得學生資料清單
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher != null) {
            Hibernate.initialize(teacher.getStudents());
        }

        return teacher != null ? new TeacherDto(teacher) : null;
    }

    @Override
    public TeacherDto getBooksByTeacherId(Long teacherId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksByTeacherId'");
    }

    @Override
    public TeacherDto addTeacher(String teacherName, List<Long> studentsId, List<Long> bookIds) {
        Teacher teacherEntity = new Teacher();
        teacherEntity.setName(teacherName);

        // 確認要新增的學生資料
        List<Student> students = studentRepository.findAllById(studentsId);
        if (students.size() != studentsId.size()) {
            throw new IllegalArgumentException("Some student Id are invalid.");
        }

        // 建立老師及學生的關聯資料
        for (Student student : students) {
            teacherEntity.getStudents().add(student);
            student.getTeachers().add(teacherEntity);
        }

        if (bookIds != null && !bookIds.isEmpty()) {
            List<Book> books = bookReporsitory.findAllById(bookIds);
            if (books.size() != bookIds.size()) {
                throw new IllegalArgumentException("Some book IDs are invalid: " + bookIds);
            }
            for (Book book : books) {
                teacherEntity.getBooks().add(book);
                book.setTeacher(teacherEntity);
            }
        }

        Teacher savedTeacher = teacherRepository.save(teacherEntity);

        return new TeacherDto(savedTeacher);
    }

}