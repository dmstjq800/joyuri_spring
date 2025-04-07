package project.demo.concert.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import project.demo.concert.entity.Concert;
import project.demo.concert.repository.ConcertRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    public List<Concert> getList() {
        return concertRepository.findAllByOrderByIdDesc();
    }
}
