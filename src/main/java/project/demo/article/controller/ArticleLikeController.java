package project.demo.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.article.service.ArticleLikeService;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleLikeController {
    private final ArticleLikeService articleLikeService;
    /// 좋아요
    @PostMapping("/likeit/{id}")
    public ResponseEntity<?> likeArticle(@PathVariable String id) {
        return articleLikeService.like(Long.parseLong(id));
    }
}
