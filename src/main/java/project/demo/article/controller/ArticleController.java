package project.demo.article.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import project.demo.security.resultdata.RsData;

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
//    @PostMapping("/write")
//    public ResponseEntity<String> writeArticle(String title, String content, @RequestParam("image") MultipartFile image) {
//        String nickname = memberService.getCurrentNickname();
//        ArticleDTO articleDTO = new ArticleDTO();
//        articleDTO.setTitle(title);
//        articleDTO.setContent(content);
//
//        //if(nickname == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
//        if(articleDTO.getTitle().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("title is empty");
//        if(image.isEmpty()) image = null;
//
//        return articleService.createArticle(articleDTO, nickname, image);
//    }
    /// 게시글 작성
    @PostMapping("/write")
    public ResponseEntity<String> writeArticle(@RequestBody ArticleDTO articleDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        /// image가 null로 올지 비어있는 상태로 올지 확인필요
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        if(articleDTO.getTitle().isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("title is empty");
        return articleService.createArticle(articleDTO, memberService.getCurrentNickname(), image);
    }
    ///  게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleDetail(@PathVariable String id) {

        return articleService.getArticleDetail(Long.parseLong(id));
    }

    /// 게시글 삭제
    @PostMapping("/{id}/delete")
    public ResponseEntity<String> deleteArticle(@PathVariable String id) {
        String nickname = memberService.getCurrentNickname();
        return articleService.deleteArticle(Long.parseLong(id), nickname);
    }
    /// 게시글 리스트
    @GetMapping("/listtest")
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
    public ResponseEntity<String> editArticle(@PathVariable String id, @RequestBody ArticleDTO articleDTO, @RequestParam(value = "image", required = false) MultipartFile image) {
        return articleService.editAticle(Long.parseLong(id), articleDTO, image);
    }

    /// 좋아요
    @PostMapping("/{id}/likeit")
    public ResponseEntity<?> likeArticle(@PathVariable String id) {
        if(articleService.findById(Long.parseLong(id)) == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no data");
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        return articleLikeService.like(Long.parseLong(id));

    }

}
