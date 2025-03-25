package project.demo.album.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime CreateDate;

    @Lob
    private String description;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<AlbumImage> albumImages = new ArrayList<>();


    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Track> Tracks = new ArrayList<>();


}
