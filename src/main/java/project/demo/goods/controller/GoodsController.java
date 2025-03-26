package project.demo.goods.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.goods.dto.GoodsListDTO;
import project.demo.goods.entity.GoodsImage;
import project.demo.goods.service.GoodsService;

import java.util.List;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    @GetMapping("/list")
    public ResponseEntity<?> getGoodsList(){
        List<GoodsListDTO> list = goodsService.getGoodsList();
        return ResponseEntity.ok(list);
    }
}
