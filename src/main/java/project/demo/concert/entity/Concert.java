package project.demo.concert.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ConcertName;
    private String place;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate concertDate;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private List<ConcertImage> concertImages = new ArrayList<>();

    public Concert(String ConcertName, String place, LocalDate concertDate) {
        this.ConcertName = ConcertName;
        this.place = place;
        this.concertDate = concertDate;
    }
}
