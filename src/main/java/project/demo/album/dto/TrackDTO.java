package project.demo.album.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.album.entity.Track;

@Getter
@Setter
@RequiredArgsConstructor
public class TrackDTO {
    private long id;
    private String title;
    private String youtubeUrl;
    private String description;

    public TrackDTO(Track track) {
        this.id = track.getId();
        this.title = track.getTitle();
        this.youtubeUrl = track.getYoutubeUrl();
        this.description = track.getDescription();
    }
}
