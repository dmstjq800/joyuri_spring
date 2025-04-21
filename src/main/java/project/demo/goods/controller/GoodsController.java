package project.demo.goods.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.goods.dto.GoodsAddDTO;
import project.demo.goods.entity.Goods;
import project.demo.goods.service.GoodsService;

import java.io.IOException;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    /// 상세
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable long id) {
        return ResponseEntity.ok(goodsService.findById(id));
    }

    /// List 반환
    @GetMapping("/list")
    public ResponseEntity<?> getGoodsList(@RequestParam(value = "sort", defaultValue = "last") String sort,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(goodsService.getGoodsList(name, sort, pageable));
    }
    /// 굿즈 추가
    @GetMapping("/add")
    public String add(){
        return "article/article";
    }

    @PostMapping("/add")
    public ResponseEntity<?> addGoods(GoodsAddDTO goodsAddDTO,
                                      @RequestParam("image") MultipartFile image) throws IOException {
        Goods goods = goodsService.addGoods(goodsAddDTO, image);
        return ResponseEntity.ok(goods);
    }
    /// 굿즈 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        Goods goods = goodsService.deleteGoods(id);
        return ResponseEntity.ok(goods.getId());
    }
    /// 굿즈 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoods(@PathVariable long id, GoodsAddDTO goodsAddDTO,
                                         @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Goods goods = goodsService.updateGoods(id, goodsAddDTO, image);
        return ResponseEntity.ok(goods.getId());
    }

}
