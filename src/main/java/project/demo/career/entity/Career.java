package project.demo.career.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String careerName;
    private String description;
    private String url;

    @JsonFormat(pattern = "yyyy.MM")
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL)
    private List<CareerImage> careerImages = new ArrayList<>();

}
