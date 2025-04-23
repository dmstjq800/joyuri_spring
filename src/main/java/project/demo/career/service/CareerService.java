package project.demo.career.service;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;
import project.demo.career.dto.CareerAddDTO;
import project.demo.career.entity.Career;
import project.demo.career.entity.CareerImage;
import project.demo.career.repository.CareerImageRepository;
import project.demo.career.repository.CareerRepository;
import project.demo.image.service.ImageService;
import project.demo.security.exeption.customexception.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final CareerRepository careerRepository;
    private final CareerImageRepository careerImageRepository;
    private final ImageService imageService;
    public List<Career> getList() {
        return careerRepository.findAllByOrderByIdDesc();
    }


    public Career addCareer(CareerAddDTO careerAddDTO, MultipartFile image) throws IOException {
        if(careerAddDTO.getCareerName() == null || careerAddDTO.getCareerName().isEmpty()) {throw new NotFoundException("Title is required");}
        Career career = Career.builder()
                .careerName(careerAddDTO.getCareerName())
                .description(careerAddDTO.getDescription())
                .releaseDate(careerAddDTO.getReleaseDate())
                .build();
        careerRepository.save(career);
        if (image != null) {
            String url = imageService.ImageUpload(image, "career/", career.getId());
            CareerImage careerImage = CareerImage.builder().career(career).url(url).build();
            careerImageRepository.save(careerImage);
        }
        return career;
    }

    public Career deleteCareer(long id) {
        Career career = careerRepository.findById(id).orElseThrow(() -> new NotFoundException("Career not found"));
        careerRepository.delete(career);
        return career;
    }
}
