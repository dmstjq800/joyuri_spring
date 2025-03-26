package project.demo.goods.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String goodsName;
    private String description;
    private int price;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL)
    private List<GoodsImage> GoodsImages = new ArrayList<>();

}
