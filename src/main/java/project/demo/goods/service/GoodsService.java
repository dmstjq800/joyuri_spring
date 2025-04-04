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
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleImage;
import project.demo.goods.dto.GoodsAddDTO;
import project.demo.goods.dto.GoodsListDTO;
import project.demo.goods.entity.Goods;
import project.demo.goods.entity.GoodsImage;
import project.demo.goods.repository.GoodsImageRepository;
import project.demo.goods.repository.GoodsRepository;
import project.demo.image.service.ImageService;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;
    private final ImageService imageService;

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
                Page<Goods> goodsPage = goodsRepository.findByGoodsNameContainingOrderByIdDesc(name, pageable);
                return ResponseEntity.ok(goodsPage);
            }
        } else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> addGoods(String name, String description, int price, MultipartFile image) {
        Goods goods = Goods.builder()
                .goodsName(name)
                .description(description)
                .price(price)
                .build();
        goodsRepository.save(goods);
        long id = goods.getId();
        if(image != null) {
            String url = imageService.ImageUpload(image, "goods/", id);
            GoodsImage goodsImage = GoodsImage.builder().goods(goods).url(url).build();
            goodsImageRepository.save(goodsImage);
        }
        return ResponseEntity.ok().build();
    }
}
