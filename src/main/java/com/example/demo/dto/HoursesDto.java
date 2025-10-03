package com.example.demo.dto;

import com.example.demo.entity.Hourses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoursesDto {

    public HoursesDto(Hourses hourses) {
        this.id = hourses.getId();
        this.name = hourses.getName();
    }

    private Long id;
    private String name;
}
