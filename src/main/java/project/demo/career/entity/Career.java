package project.demo.career.entity;


import jakarta.persistence.*;
import lombok.*;

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

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL)
    private List<CareerImage> careerImages = new ArrayList<>();

}
