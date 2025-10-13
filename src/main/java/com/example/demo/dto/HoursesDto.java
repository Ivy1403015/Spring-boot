package com.example.demo.dto;

import com.example.demo.entity.Hourses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HoursesDto {

    private Long id;
    private String name;
    private Long bookId;

    public HoursesDto(Hourses hourses) {
        this.id = hourses.getId();
        this.name = hourses.getName();
        if (hourses.getBook() != null) {
            this.bookId = hourses.getBook().getId();
        }
    }
}
