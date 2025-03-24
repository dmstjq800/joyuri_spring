package project.demo.album.dto;


import lombok.Getter;
import lombok.Setter;
import project.demo.album.entity.Album;

@Getter
@Setter
public class AlbumDTO {

    private long id;
    private String title;
    private String description;
    private byte[] image;

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.image = album.getImage();
    }
}
