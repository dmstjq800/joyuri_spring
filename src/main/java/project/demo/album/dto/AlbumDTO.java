package project.demo.album.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<AlbumImage> albumImages;
    private String tags;

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.releaseDate = album.getReleaseDate();
        this.albumImages = album.getAlbumImages();
        this.tags = album.getTags();
    }
}
