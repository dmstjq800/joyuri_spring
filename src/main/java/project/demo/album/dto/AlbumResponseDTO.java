package project.demo.album.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.demo.album.entity.Album;
import project.demo.album.entity.AlbumImage;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumResponseDTO {
    private long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private List<AlbumImage> albumImages;
    private String tags;

    public AlbumResponseDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.releaseDate = album.getReleaseDate();
        this.albumImages = album.getAlbumImages();
        this.tags = album.getTags();
    }
}
