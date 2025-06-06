package project.demo.album.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumAddDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String tags;
}
