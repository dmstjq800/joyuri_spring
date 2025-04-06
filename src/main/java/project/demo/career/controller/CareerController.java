package project.demo.career.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.career.service.CareerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerController {
    private final CareerService careerService;

    @GetMapping("/list")
    public ResponseEntity<?> getCareerList() {
        return ResponseEntity.ok(careerService.getList());
    }
}
