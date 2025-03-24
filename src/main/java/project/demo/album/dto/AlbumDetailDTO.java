package project.demo.album.dto;

import project.demo.album.entity.Album;
import project.demo.album.entity.Track;
import project.demo.article.entity.Comment;

import java.util.List;

public class AlbumDetailDTO {
    private long id;
    private String title;
    private String description;
    private byte[] image;
    private List<Track> Tracks;
    public AlbumDetailDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.description = album.getDescription();
        this.image = album.getImage();
        this.Tracks = album.getTracks();
    }
}
