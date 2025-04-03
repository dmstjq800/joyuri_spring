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


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class CommetController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final ArticleService articleService;
    /// 댓글 작성
    @PostMapping("/{id}/insertComment")
    public ResponseEntity<String> insertComment(@PathVariable String id, @RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        String nickname = memberService.getCurrentNickname();

        return commentService.insertComment(Long.parseLong(id), commentDTO, nickname);
    }
    /// 댓글 삭제
    @PostMapping("/deleteComment")
    public ResponseEntity<String> deleteCommnet(@RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");

        return commentService.deleteCommnet(commentDTO);
    }

    /// 댓글 리스트
    @GetMapping("/{id}/commentList")
    public ResponseEntity <List<CommentDTO>> commentList(@PathVariable String id) {
        if(articleService.findById(Long.parseLong(id)) == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(commentService.getCommentList(Long.parseLong(id)));
    }
    /// 대댓글 리스트
    @GetMapping("/getChild")
    public ResponseEntity<?> childList(String id) {
        return commentService.findByParentId(Long.parseLong(id));
    }
    /// 대댓글 작성
    @PostMapping("/insertChildren")
    public ResponseEntity<String> insertChildren(@RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");

        return commentService.insertChildren(commentDTO);
    }


}
