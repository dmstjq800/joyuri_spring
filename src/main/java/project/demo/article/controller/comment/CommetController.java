package project.demo.article.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.CommentDTO;
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
    public ResponseEntity<?> insertComment(@PathVariable long id, @RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        Comment comment = commentService.insertComment(id, commentDTO);
        return ResponseEntity.ok("success : " + comment.getId());
    }
    /// 댓글 삭제
    @PostMapping("/deleteComment")
    public ResponseEntity<String> deleteCommnet(@RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        Comment comment = commentService.deleteCommnet(commentDTO);
        return ResponseEntity.ok("success : " + comment.getId());
    }
    /// 대댓글 작성
    @PostMapping("/insertChildren")
    public ResponseEntity<String> insertChildren(@RequestBody CommentDTO commentDTO) {
        if(!memberService.isLogined()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        Comment comment = commentService.insertChildren(commentDTO);
        return ResponseEntity.ok("success : " + comment.getId());
    }
}
