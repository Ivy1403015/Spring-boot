package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Hourses;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.BookReporsitory;
import com.example.demo.repository.HoursesRepository;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookReporsitory bookReporsitory;
    private final TeacherRepository teacherRepository;
    private final HoursesRepository hoursesRepository;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookReporsitory.findAll();
        return books.stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = bookReporsitory.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        return new BookDto(book);

    }

    @Override
    public BookDto createBook(BookDto book) {
        Book bookEntity = new Book(book);

        if (book.getHoursesId() != null) {
            Hourses hourses = hoursesRepository.findById(book.getHoursesId())
                    .orElseThrow(() -> new RuntimeException("Hourses not found with id: " + book.getHoursesId()));
            bookEntity.setHourses(hourses);
        }

        // 設置 Teacher 關聯
        if (book.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(book.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + book.getTeacherId()));
            bookEntity.setTeacher(teacher);
        }

        Book savedBook = bookReporsitory.save(bookEntity);
        return new BookDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookDto book) {
        Book bookEntity = bookReporsitory.findById(id).get();
        bookEntity.setName(book.getName());
        if (book.getHoursesId() != null) {
            Hourses hourses = hoursesRepository.findById(book.getHoursesId())
                    .orElseThrow(() -> new RuntimeException("Hourses not found with id: " + book.getHoursesId()));
            bookEntity.setHourses(hourses);
        }
        if (book.getTeacherId() != null) {

            Teacher teacherData = teacherRepository.findById(book.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + book.getTeacherId()));

            bookEntity.setTeacher(teacherData);
        }
        Book savedBook = bookReporsitory.save(bookEntity);
        return new BookDto(savedBook);
    }

    @Override
    public void deleteBook(Long id) {

        Book book = bookReporsitory.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        // 刪除book和teacher的關聯
        if (book.getTeacher() != null) {
            book.getTeacher().getBooks().remove(book);
            book.setTeacher(null);
        }

        // 刪除book和hourses的關聯
        if (book.getHourses() != null) {
            book.getHourses().setBook(null);
            book.setHourses(null);
        }

        bookReporsitory.deleteById(id);
    }

}
