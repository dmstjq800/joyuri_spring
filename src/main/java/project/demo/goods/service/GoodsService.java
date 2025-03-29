package project.demo.goods.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
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

    public ResponseEntity<?> findById(long id) {
        return ResponseEntity.ok(goodsRepository.findById(id));
    }

    public ResponseEntity<?> getGoodsList(String name, String sort, Pageable pageable) {

        if (name != null) {
            // 이름으로 검색
            if (sort != null && sort.equalsIgnoreCase("asc")) {
                Page<Goods> goodsPage = goodsRepository.findByGoodsNameContainingIgnoreCaseOrderByPriceAsc(name, pageable);
                return ResponseEntity.ok(goodsPage);
            } else if (sort != null && sort.equalsIgnoreCase("desc")) {
                Page<Goods> goodsPage = goodsRepository.findByGoodsNameContainingIgnoreCaseOrderByPriceDesc(name, pageable);
                return ResponseEntity.ok(goodsPage);
            } else {
                Page<Goods> goodsPage = goodsRepository.findByGoodsNameContaining(name, pageable);
                return ResponseEntity.ok(goodsPage);
            }
        } else {
            // 이름없이 가격만으로 정렬
            if (sort != null && sort.equalsIgnoreCase("asc")) {
                Page<Goods> goodsPage = goodsRepository.findAllByOrderByPriceAsc(pageable);
                return ResponseEntity.ok(goodsPage);
            } else if (sort != null && sort.equalsIgnoreCase("desc")) {
                Page<Goods> goodsPage = goodsRepository.findAllByOrderByPriceDesc(pageable);
                return ResponseEntity.ok(goodsPage);
            } else if (sort != null && sort.equals("last")) {
                Page<Goods> goodsPage = goodsRepository.findAllByOrderByIdDesc(pageable);
                return ResponseEntity.ok(goodsPage);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sort parameter");
            }
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        for(int i = 0; i < 20; i++){
            Goods goods = Goods.builder()
                    .goodsName("test "+i)
                    .description("Test " + i)
                    .price(5000*i)
                    .build();
            goodsRepository.save(goods);
            GoodsImage goodsImage = GoodsImage
                    .builder()
                    .url("/images/goods/goods" + (i%12+1) + ".png")
                    .goods(goods).
                    build();
            goodsImageRepository.save(goodsImage);
        }
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

        for(int i = 21; i < 26; i++){
            goods = goodsRepository.findById((long) i).orElse(null);
            GoodsImage goodsImage = GoodsImage.builder()
                    .url("/images/goods/goods" + (i-20) + ".png")
                    .goods(goods)
                    .build();
            goodsImageRepository.save(goodsImage);
        }
    }
}
