package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.HoursesDto;
import com.example.demo.service.HoursesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HoursesController {

    private final HoursesService hoursesService;

    @GetMapping("/api/hourses/{id}")
    public HoursesDto getHourses(@PathVariable Long id) {
        return hoursesService.getHourses(id);
    }

    @PostMapping("/api/hourses")
    public HoursesDto createHourses(@RequestBody HoursesDto hourses) {
        return hoursesService.addHourses(hourses);
    }

    @PatchMapping("/api/hourses/{id}")
    public HoursesDto updateHourses(@PathVariable Long id, @RequestBody HoursesDto hourses) {
        return hoursesService.updateHoueses(id, hourses);
    }

    @DeleteMapping("/api/hourses/{id}")
    public void deleteHourses(@PathVariable Long id) {
        hoursesService.deleteHourede(id);
    }

}
