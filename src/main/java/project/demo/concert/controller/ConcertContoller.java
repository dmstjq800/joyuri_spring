package project.demo.concert.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.concert.service.ConcertService;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertContoller {
    private final ConcertService concertService;

    @GetMapping("/list")
    public ResponseEntity<?> getConcerts() {
        return ResponseEntity.ok(concertService.getList());
    }
}
