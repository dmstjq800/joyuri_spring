package project.demo.album.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AlbumAddDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String tags;
}
