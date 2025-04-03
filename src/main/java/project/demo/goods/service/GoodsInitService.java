package project.demo.goods.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import project.demo.goods.entity.Goods;
import project.demo.goods.entity.GoodsImage;
import project.demo.goods.repository.GoodsImageRepository;
import project.demo.goods.repository.GoodsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsInitService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;
    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        if (goodsRepository.count() > 0){ return;}
        goodsRepository.saveAll(List.of(
                new Goods("쪼율봉", "응원봉", 44000),
                new Goods("쪼율의 포토북", "포토북", 23000),
                new Goods("쪼율의 가을 포토북", "포토북", 27000),
                new Goods("글래시 앨범", "앨범", 56000),
                new Goods("쪼율 슬로건", "슬로건", 16000),
                new Goods("쪼율 티셔츠", "티셔츠", 33000),
                new Goods("쪼율 수건", "수건", 12000),
                new Goods("율 폰 케이스", "휴대폰 케이스", 37000),
                new Goods("율링 폰 케이스", "휴대폰 케이스", 37000),
                new Goods("유리한 케이스", "휴대폰 케이스", 37000),
                new Goods("쪼율 머리띠(검정)", "머리띠", 22000),
                new Goods("브이율 케이스", "휴대폰 케이스", 42000),
                new Goods("율링 머리띠(주황)", "머리띠", 22000),
                new Goods("율봉", "응원봉", 37800)
        ));
        for(int i = 1; i < 15; i++){
            Goods goods = goodsRepository.findById((long) i).orElse(null);
            GoodsImage goodsImage = GoodsImage.builder()
                    .url("/images/goods/goods" + (i) + ".png")
                    .goods(goods)
                    .build();
            goodsImageRepository.save(goodsImage);
        }
    }
}
