package project.demo.goods.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.CommentDTO;
import project.demo.goods.entity.Goods;
import project.demo.goods.service.GoodsService;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;

    /// 상세
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable String id) {
        return goodsService.findById(Long.parseLong(id));
    }

    /// List 반환
    @GetMapping("/list")
    public ResponseEntity<?> getGoodsList2(@RequestParam(value = "sort", defaultValue = "last") String sort,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return goodsService.getGoodsList(name, sort, pageable);
    }


}
