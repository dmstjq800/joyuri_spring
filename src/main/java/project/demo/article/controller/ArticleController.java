package project.demo.article.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.ArticleRequestDTO;
import project.demo.article.entity.Article;

import project.demo.article.service.ArticleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/write")
    public String write() {
        return "article/article";
    }
    /// 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<?> writeArticle(ArticleRequestDTO articleRequestDTO,
                                               @RequestParam(value = "image", required = false) MultipartFile image) {
        Article article = articleService.createArticle(articleRequestDTO, image);
        return ResponseEntity.ok("success : " + article.getId());
    }
    /// 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable long id) {
        Article article = articleService.deleteArticle(id);
        return ResponseEntity.ok("success : " + article.getId());
    }
    /// 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> editArticle(@PathVariable long id, ArticleRequestDTO articleRequestDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        Article article = articleService.editArticle(id, articleRequestDTO, image);
        return ResponseEntity.ok("success : " + article.getId());
    }
}
