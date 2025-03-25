package project.demo.album.dto;

import lombok.Getter;
import lombok.Setter;
import project.demo.album.entity.Album;
import project.demo.album.entity.Track;
import project.demo.album.entity.AlbumImage;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AlbumDetailDTO {
    private long id;
    private String title;
    private String description;
    private List<AlbumImage> albumImages;
    private LocalDate CreateDate;
    private List<Track> Tracks;
    public AlbumDetailDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.albumImages = album.getAlbumImages();
        this.Tracks = album.getTracks();
        this.CreateDate = LocalDate.now();
    }
}
