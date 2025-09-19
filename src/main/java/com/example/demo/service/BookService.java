package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.BookDto;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(BookDto book);

    BookDto updateBook(Long id, BookDto book);

    void deleteBook(Long id);
}
