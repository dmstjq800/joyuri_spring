package project.demo.article.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
import project.demo.article.service.ArticleService;
import project.demo.member.service.MemberService;
import project.demo.security.resultdata.RsData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    /// 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeArticle(@RequestBody ArticleDTO articleDTO) {
        String username = memberService.getCurrentUsername();
        if(username == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        if(articleDTO.getTitle().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("title is empty");

        return articleService.createArticle(articleDTO.getTitle(), articleDTO.getContent(), username);
    }
    ///  게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleDetail(@PathVariable String id) {
        if(articleService.findById(Long.parseLong(id)) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no data");
        return ResponseEntity.ok(articleService.getArticleDetail(Long.parseLong(id)));
    }

    /// 게시글 삭제
    @PostMapping("/delete{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable String id) {
        String username = memberService.getCurrentUsername();
        return articleService.deleteArticle(Long.parseLong(id), username);
    }
    /// 게시글 리스트
    @GetMapping("/list")
    public List<ArticleListDTO> listArticle() {
        return articleService.getArticlelist();
    }

    @PostMapping("/test")
    public String test(){
        return memberService.getCurrentUsername();
    }
}
