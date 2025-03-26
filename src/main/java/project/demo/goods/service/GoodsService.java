package project.demo.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Service;
import project.demo.goods.dto.GoodsListDTO;
import project.demo.goods.entity.Goods;
import project.demo.goods.entity.GoodsImage;
import project.demo.goods.repository.GoodsImageRepository;
import project.demo.goods.repository.GoodsRepository;



import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;

    public List<GoodsListDTO> getGoodsList() {
        List<Goods> goodsList = goodsRepository.findAll();
        List<GoodsListDTO> goodsListDTOList = new ArrayList<>();
        for (Goods goods : goodsList) {
            goodsListDTOList.add(new GoodsListDTO(goods));
        }
        return goodsListDTOList;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        Goods goods = Goods.builder()
                .goodsName("쪼율봉")
                .description("응원을 더 활기차게")
                .price(44000)
                .build();
        goodsRepository.save(goods);
        goods = Goods.builder()
                .goodsName("쪼율의 포토북")
                .description("나이승")
                .price(22000)
                .build();
        goodsRepository.save(goods);
        goods = Goods.builder()
                .goodsName("쪼율의 가을 포토북")
                .description("풍성하고 더 활기차게")
                .price(24000)
                .build();
        goodsRepository.save(goods);
        goods = Goods.builder()
                .goodsName("글래시 앨범")
                .description("전세계를 놀라게한 1집 앨범")
                .price(62000)
                .build();
        goodsRepository.save(goods);
        goods = Goods.builder()
                .goodsName("쪼율 슬로건")
                .description("슬로건")
                .price(12000)
                .build();
        goodsRepository.save(goods);

        for(int i = 1; i < 6; i++){
            goods = goodsRepository.findById((long) i).orElse(null);
            GoodsImage goodsImage = GoodsImage.builder()
                    .url("/images/goods/goods" + i + ".png")
                    .goods(goods)
                    .build();
            goodsImageRepository.save(goodsImage);
        }
    }

}
