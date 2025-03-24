package project.demo.album.dto;


import lombok.Getter;
import lombok.Setter;
import project.demo.album.entity.Track;

@Getter
@Setter
public class TrackDTO {
    private long id;
    private String title;
    private String youtubeUrl;
    private String Description;

    public TrackDTO(Track track) {
        this.id = track.getId();
        this.title = track.getTitle();
        this.youtubeUrl = track.getYoutubeUrl();
        this.Description = track.getDescription();
    }
}
