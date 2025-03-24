package project.demo.album.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String youtubeUrl;

    private String Description;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
