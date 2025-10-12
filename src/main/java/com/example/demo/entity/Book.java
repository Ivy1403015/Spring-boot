package com.example.demo.entity;

import com.example.demo.dto.BookDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "book")
public class Book {

    public Book(BookDto bookDto) {
        this.id = bookDto.getId();
        this.name = bookDto.getName();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacherId")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "hoursesId")
    private Hourses hourses;

}
