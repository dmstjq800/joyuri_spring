package project.demo.goods.dto;


import lombok.Getter;
import lombok.Setter;
import project.demo.goods.entity.Goods;
import project.demo.goods.entity.GoodsImage;

import java.util.List;

@Getter
@Setter
public class GoodsListDTO {
    private Long id;
    private String goodsName;
    private int price;
    private List<GoodsImage> GoodsImages;
    public GoodsListDTO(Goods goods) {
        this.id = goods.getId();
        this.goodsName = goods.getGoodsName();
        this.price = goods.getPrice();
        this.GoodsImages = goods.getGoodsImages();
    }
}
