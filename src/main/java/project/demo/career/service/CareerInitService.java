package project.demo.career.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import project.demo.career.entity.Career;
import project.demo.career.entity.CareerImage;
import project.demo.career.repository.CareerImageRepository;
import project.demo.career.repository.CareerRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CareerInitService {
    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        if(careerRepository.count() > 0){ return;}
        Career career = Career.builder()
                .careerName("Produce48")
                .description("한일합작 아이돌 서바이벌 프로젝트")
                .releaseDate(LocalDateTime.of(2018, 6, 15, 0, 0))
                .url("https://www.youtube.com/playlist?list=PLIBmjIHzZF9lx2I5GyZGJtQRbNt7_UMGD")
                .build();
        careerRepository.save(career);
        career = Career.builder()
                .careerName("잇힝트립2")
                .description("힐링 여행")
                .releaseDate(LocalDateTime.of(2020, 12, 30, 0, 0))
                .url("https://www.youtube.com/hashtag/%EC%9E%87%ED%9E%9D%ED%8A%B8%EB%A6%BD")
                .build();
        careerRepository.save(career);
        career = Career.builder()
                .careerName("미미쿠스")
                .description("좌충우돌 학교생활")
                .releaseDate(LocalDateTime.of(2022, 7, 22, 0, 0))
                .url("https://www.youtube.com/playlist?list=PL920OTfqSyzf-eWcz3p9GEvHZk99_wbss")
                .build();
        careerRepository.save(career);
        career = Career.builder()
                .careerName("오징어게임 시즌2")
                .description("전세계를 강타한 대히트작")
                .releaseDate(LocalDateTime.of(2024, 12, 26, 0, 0))
                .url("https://www.youtube.com/watch?v=IxMDDSa2cNw")
                .build();
        careerRepository.save(career);
        for (int i = 1; i < 5; i++){
            CareerImage careerImage = CareerImage.builder()
                    .url("/images/career/career" + i + ".png")
                    .career(careerRepository.findById((long) i).orElse(null))
                    .build();
            careerImageRepository.save(careerImage);
        }

    }
}
