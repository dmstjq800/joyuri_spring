package project.demo.article.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.ArticleDTO;
import project.demo.article.dto.CommentDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.Comment;
import project.demo.article.service.ArticleService;
import project.demo.article.service.CommentService;
import project.demo.member.service.MemberService;
import project.demo.security.resultdata.RsData;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article/{id}")
public class CommetController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final ArticleService articleService;
    /// 댓글 작성
    @PostMapping("/insertComment")
    public ResponseEntity<String> insertComment(@PathVariable String id, @RequestBody ArticleDTO articleDTO) {
        String nickname = memberService.getCurrentNickname();
        String content = articleDTO.getContent();
        if(nickname == null)  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("require login");
        }

        Article article = articleService.findById(Long.parseLong(id));
        if(article == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");

        return commentService.insertComment(article, content, nickname);
    }
    /// 댓글 삭제
    @PostMapping("/deleteComment")
    public ResponseEntity<String> deleteCommnet(@PathVariable String id, String commentId) {
        String nickname = memberService.getCurrentNickname();
        Article article = articleService.findById(Long.parseLong(id));
        if(nickname == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("require login");
        if(article == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");

        return commentService.deleteCommnet(Long.parseLong(commentId), article, nickname);
    }

    /// 댓글 리스트
    @GetMapping("/commentList")
    public ResponseEntity <List<CommentDTO>> commentList(@PathVariable String id) {
        if(articleService.findById(Long.parseLong(id)) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(commentService.getCommentList(Long.parseLong(id)));

    }

    @PostMapping("/test")
    public List<Comment> test(@PathVariable String id) {
       List<Comment> commentList = articleService.getCommentsByArticleId(Long.parseLong(id));
       return commentList;

    }

}
