package project.demo.album.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Album {
    public Album(String albumTitle, String description, LocalDate releaseDate, String tags) {
        this.title = albumTitle;
        this.releaseDate = releaseDate;
        this.description = description;
        this.tags = tags;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate releaseDate;

    @Lob
    private String description;

    private String tags;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<AlbumImage> albumImages = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> tracks = new ArrayList<>();

    public void addTrack(Track track) {
        this.tracks.add(track);
        track.setAlbum(this);
    }


}
