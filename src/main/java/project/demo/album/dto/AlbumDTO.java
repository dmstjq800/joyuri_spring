package project.demo.album.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.demo.album.entity.Album;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private String title;
    private String description;
    private String image;

    public AlbumDTO(Album album) {
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.image = album.getImage();
    }
}
