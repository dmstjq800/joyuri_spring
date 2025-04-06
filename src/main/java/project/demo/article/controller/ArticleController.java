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

@Controller
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
    public ResponseEntity<?> writeArticle(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam(value = "image", required = false) MultipartFile image) {
        Article article = articleService.createArticle(title, content, image);
        return ResponseEntity.ok("success : " + article.getId());
    }
    /// 게시글 삭제
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        Article article = articleService.deleteArticle(id);
        return ResponseEntity.ok("success : " + article.getId());
    }
    /// 게시글 수정
    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<String> editArticle(@PathVariable Long id, @RequestBody ArticleRequestDTO articleRequestDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        Article article = articleService.editArticle(id, articleRequestDTO, image);
        return ResponseEntity.ok("success : " + article.getId());
    }
}
