package project.demo.article.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.ArticleDetailDTO;
import project.demo.article.dto.ArticleListDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleLike;
import project.demo.article.repository.ArticleLikeRepository;
import project.demo.article.service.ArticleLikeService;
import project.demo.article.service.ArticleService;
import project.demo.member.dto.MemberDTO;
import project.demo.member.entity.Member;
import project.demo.member.service.MemberService;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;
    private final ArticleLikeService articleLikeService;


    @GetMapping("/write")
    public String write() {
        return "article/article";
    }

    /// 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeArticle(@RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam(value = "image", required = false) MultipartFile image) {
        if(title == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("title is empty");
        return articleService.createArticle(title, content,  image);
    }
    ///  게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleDetail(@PathVariable String id) {

        return articleService.getArticleDetail(Long.parseLong(id));
    }

    /// 게시글 삭제
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<String> deleteArticle(@PathVariable String id) {
        return articleService.deleteArticle(Long.parseLong(id));
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
        return articleService.getArticle4entity();
    }
    /// 게시글 페이징
    @GetMapping("/list")
    public ResponseEntity<?> getArticleList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9") int size) {
        return articleService.getArticlesPerPage(page, size);
    }
    /// 게시글 수정
    @PostMapping("/{id}/edit")
    @PreAuthorize("hasAnyRole('ADMIN', 'ARTIST')")
    public ResponseEntity<String> editArticle(@PathVariable String id, @RequestBody ArticleDTO articleDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        return articleService.editAticle(Long.parseLong(id), articleDTO, image);
    }

    /// 좋아요
    @PostMapping("/{id}/likeit")
    public ResponseEntity<?> likeArticle(@PathVariable String id) {
        return articleLikeService.like(Long.parseLong(id));
    }

}
