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
import project.demo.security.exeption.customexception.NotFoundException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {
    private final GoodsRepository goodsRepository;
    private final GoodsImageRepository goodsImageRepository;
    private final ImageService imageService;

    public Goods findById(long id) {
        return goodsRepository.findById(id).orElseThrow(() -> new NotFoundException("goods not found"));
    }
    /// 굿즈 페이지 리스트
    public Page<Goods> getGoodsList(String name, String sort, Pageable pageable) {
        Page<Goods> goodsPage;
        if (sort != null && sort.equalsIgnoreCase("asc")) {
            goodsPage = goodsRepository.findByGoodsNameContainingIgnoreCaseOrderByPriceAsc(name, pageable);

        } else if (sort != null && sort.equalsIgnoreCase("desc")) {
            goodsPage = goodsRepository.findByGoodsNameContainingIgnoreCaseOrderByPriceDesc(name, pageable);

        } else {
            goodsPage = goodsRepository.findByGoodsNameContainingOrderByIdDesc(name, pageable);
        }
        return goodsPage;
    }
    ///  굿즈 추가
    public Goods addGoods(GoodsAddDTO goodsAddDTO, MultipartFile image) throws IOException {
        Goods goods = Goods.builder()
                .goodsName(goodsAddDTO.getName())
                .description(goodsAddDTO.getDescription())
                .price(goodsAddDTO.getPrice())
                .build();
        goodsRepository.save(goods);
        long id = goods.getId();
        if(image != null) {
            String url = imageService.ImageUpload(image, "goods/", id);
            GoodsImage goodsImage = GoodsImage.builder().goods(goods).url(url).build();
            goodsImageRepository.save(goodsImage);
        }
        return goods;
    }

    public Goods deleteGoods(long id) {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new NotFoundException("goods not found"));
        goodsRepository.delete(goods);
        return goods;
    }

    public Goods updateGoods(long id, GoodsAddDTO goodsAddDTO, MultipartFile image) throws IOException {
        Goods goods = goodsRepository.findById(id).orElseThrow(() -> new NotFoundException("goods not found"));
        goods.setGoodsName(goodsAddDTO.getName());
        goods.setDescription(goodsAddDTO.getDescription());
        goods.setPrice(goodsAddDTO.getPrice());
        goodsRepository.save(goods);
        if(image != null) {
            String url = imageService.ImageUpload(image, "goods/", goods.getId());
            GoodsImage goodsImage = goodsImageRepository.findByGoods(goods).orElse(null);
            if(goodsImage == null){
                goodsImage = GoodsImage.builder().goods(goods).url(url).build();
            }
            else goodsImage.setUrl(url);
            goodsImageRepository.save(goodsImage);
        }
        return goods;
    }
}
