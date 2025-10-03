package com.example.demo.service;

import com.example.demo.dto.HoursesDto;

public interface HoursesService {
    HoursesDto getHourses(Long id);

    HoursesDto addHourses(HoursesDto hourses);

    HoursesDto updateHoueses(Long id, HoursesDto hourses);

    void deleteHourede(Long id);
}
