package project.demo.concert.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.concert.dto.ConcertAddDTO;
import project.demo.concert.entity.Concert;
import project.demo.concert.service.ConcertService;

@RestController
@RequestMapping("/concert")
@RequiredArgsConstructor
public class ConcertController {
    private final ConcertService concertService;

    @GetMapping("/list")
    public ResponseEntity<?> getConcerts() {
        return ResponseEntity.ok(concertService.getList());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addConcert(@RequestBody ConcertAddDTO concertAddDTO) {
        Concert concert = concertService.addConcert(concertAddDTO);
        return ResponseEntity.ok("success : "+ concert.getId());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConcert(@PathVariable long id) {
        Concert concert = concertService.deleteConcert(id);
        return ResponseEntity.ok("success : "+ concert.getId());
    }
}
