package project.demo.career.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.dto.AlbumAddDTO;
import project.demo.album.entity.Album;
import project.demo.career.dto.CareerAddDTO;
import project.demo.career.entity.Career;
import project.demo.career.service.CareerService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/career")
public class CareerController {
    private final CareerService careerService;

    @GetMapping("/list")
    public ResponseEntity<?> getCareerList() {
        return ResponseEntity.ok(careerService.getList());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCareer(CareerAddDTO careerAddDTO,
                                      @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Career career = careerService.addCareer(careerAddDTO, image);
        return ResponseEntity.ok("success : "+ career.getId());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable long id) {
        Career career = careerService.deleteCareer(id);
        return ResponseEntity.ok("success : "+ career.getId());
    }
}
