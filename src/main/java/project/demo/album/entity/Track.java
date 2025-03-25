package project.demo.album.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String youtubeUrl;

    @Lob
    private String Description;

    @ManyToOne
    @JoinColumn(name = "album_id")
    @JsonIgnore
    private Album album;
}
