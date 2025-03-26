package project.demo.goods.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import project.demo.article.entity.Article;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GoodsImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    @JsonIgnore
    private Goods goods;
    public void updateUrl(String url) {
        this.url = url;
    }
}
