package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Teacher;
import com.example.demo.repository.BookReporsitory;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookReporsitory bookReporsitory;
    private final TeacherRepository teacherRepository;

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

        // 設置 Teacher 關聯
        if (book.getTeacher() != null && book.getTeacher().getId() != null) {
            Teacher teacher = teacherRepository.findById(book.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + book.getTeacher().getId()));
            bookEntity.setTeacher(teacher);
        }

        Book savedBook = bookReporsitory.save(bookEntity);
        return new BookDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookDto book) {
        Book bookEntity = bookReporsitory.findById(id).get();
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        Book savedBook = bookReporsitory.save(bookEntity);
        return new BookDto(savedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookReporsitory.deleteById(id);
    }

}
