package project.demo.career.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CareerAddDTO {
    private String careerName;
    private String description;


    private LocalDateTime releaseDate;
}
