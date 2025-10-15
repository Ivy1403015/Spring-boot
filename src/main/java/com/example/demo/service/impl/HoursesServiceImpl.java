package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.HoursesDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Hourses;
import com.example.demo.repository.BookReporsitory;
import com.example.demo.repository.HoursesRepository;
import com.example.demo.service.HoursesService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HoursesServiceImpl implements HoursesService {

    private final HoursesRepository hoursesRepository;
    private final BookReporsitory bookReporsitory;

    @Override
    public HoursesDto getHourses(Long id) {
        Hourses hourses = hoursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Hourses not found"));
        return new HoursesDto(hourses);
    }

    @Override
    public HoursesDto addHourses(HoursesDto hourses) {
        Hourses hoursesEntity = new Hourses(hourses);
        Book book = new Book();

        if (hourses.getBook() != null) {

            book.setName(hourses.getBook().getName());

            // 設定關聯
            book.setHourses(hoursesEntity); // 將hourses的關聯新增至book中
            hoursesEntity.setBook(book);
        } else if (hourses.getBookId() != null) {

            book = bookReporsitory.findById(hourses.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + hourses.getBookId()));

            book.setHourses(hoursesEntity); // 將hourses的關聯新增至book中
            hoursesEntity.setBook(book);
        }

        Hourses saveHourses = hoursesRepository.save(hoursesEntity);
        return new HoursesDto(saveHourses);
    }

    @Transactional
    @Override
    public HoursesDto updateHoueses(Long id, HoursesDto hourses) {
        Hourses hoursesEntity = hoursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hourses not found"));

        // 確認更新的名稱不為null
        if (hourses.getName() != null) {
            hoursesEntity.setName(hourses.getName());

        }

        // 設定Book關聯更新
        Book book = new Book();
        if (hourses.getBookId() != null) {
            book = bookReporsitory.findById(hourses.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + hourses.getBookId()));
            book.setHourses(hoursesEntity);
            hoursesEntity.setBook(book);
        }

        Hourses saveHourses = hoursesRepository.save(hoursesEntity);
        return new HoursesDto(saveHourses);
    }

    @Override
    public void deleteHourede(Long id) {
        Hourses hourses = hoursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Hourses not found!"));

        // 清除Book關聯
        if (hourses.getBook() != null) {
            hourses.getBook().setHourses(null);// book為擁有方，設定FK為null
            hourses.setBook(null); // 清除book對hourses的關聯
        }
        hoursesRepository.deleteById(id);
    }

}
