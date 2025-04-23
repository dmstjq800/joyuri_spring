package project.demo.concert.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ConcertAddDTO {
    private String concertName;
    private String place;
    private LocalDate concertDate;
}
