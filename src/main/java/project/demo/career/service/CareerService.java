package project.demo.career.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.career.entity.Career;
import project.demo.career.entity.CareerImage;
import project.demo.career.repository.CareerImageRepository;
import project.demo.career.repository.CareerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;
    public ResponseEntity<?> getList() {
        List<Career> careerList = careerRepository.findAllByOrderByIdDesc();
        return ResponseEntity.ok(careerList);
    }




}
