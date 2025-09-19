package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookDto;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return books;
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return book;
    }

    @PostMapping("/books")
    public BookDto createBook(@RequestBody BookDto book) {
        BookDto createdBook = bookService.createBook(book);
        return createdBook;
    }

    @PutMapping("/books/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto book) {
        BookDto updatedBook = bookService.updateBook(id, book);
        return updatedBook;
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
