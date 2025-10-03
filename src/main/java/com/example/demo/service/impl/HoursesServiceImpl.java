package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.HoursesDto;
import com.example.demo.entity.Hourses;
import com.example.demo.repository.HoursesRepository;
import com.example.demo.service.HoursesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoursesServiceImpl implements HoursesService {

    private final HoursesRepository hoursesRepository;

    @Override
    public HoursesDto getHourses(Long id) {
        Hourses hourses = hoursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Hourses not found"));
        return new HoursesDto(hourses);
    }

    @Override
    public HoursesDto addHourses(HoursesDto hourses) {
        Hourses hoursesEntity = new Hourses(hourses);
        Hourses saveHourses = hoursesRepository.save(hoursesEntity);
        return new HoursesDto(saveHourses);
    }

    @Override
    public HoursesDto updateHoueses(Long id, HoursesDto hourses) {
        Hourses hoursesEntity = hoursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hourses not found"));
        hoursesEntity.setName(hourses.getName());
        Hourses saveHourses = hoursesRepository.save(hoursesEntity);
        return new HoursesDto(saveHourses);
    }

    @Override
    public void deleteHourede(Long id) {
        hoursesRepository.deleteById(id);
    }

}
