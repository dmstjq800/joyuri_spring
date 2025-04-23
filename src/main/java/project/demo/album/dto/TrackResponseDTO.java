package project.demo.album.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import project.demo.album.entity.Track;

@Getter
@Setter
@RequiredArgsConstructor
public class TrackResponseDTO {
    private long id;
    private String title;
    private String youtubeUrl;
    private String description;

    public TrackResponseDTO(Track track) {
        this.id = track.getId();
        this.title = track.getTitle();
        this.youtubeUrl = track.getYoutubeUrl();
        this.description = track.getDescription();
    }
}
