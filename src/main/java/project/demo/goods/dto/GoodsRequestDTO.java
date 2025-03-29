package project.demo.goods.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsRequestDTO {
    private String name;
    private String sort;

    public GoodsRequestDTO() {
        this.sort = "last";
    }
}
