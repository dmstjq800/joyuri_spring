package project.demo.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.service.ArticleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleQueryController {
    private final ArticleService articleService;

    ///  게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleDetail(@PathVariable long id) {
        ArticleDetailDTO articleDetailDTO = articleService.getArticleDetail(id);
        return ResponseEntity.ok(articleDetailDTO);
    }
    /// 게시글 리스트
    @GetMapping("/listtest")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<List<ArticleListDTO>> listArticle() {
        return ResponseEntity.ok(articleService.getArticlelist());
    }
    /// 게시글 상위 4개
    @GetMapping("/getfour")
    public ResponseEntity<?> getArticle4Entity(){
        return ResponseEntity.ok(articleService.getArticle4entity());
    }
    /// 게시글 페이징
    @GetMapping("/list")
    public ResponseEntity<?> getArticleList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {
        return ResponseEntity.ok(articleService.getArticlesPerPage(page, size));
    }
}
