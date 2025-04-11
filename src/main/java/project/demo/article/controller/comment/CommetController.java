package project.demo.article.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.demo.article.dto.CommentRequestDTO;
import project.demo.article.dto.CommentResponseDTO;
import project.demo.article.entity.Comment;
import project.demo.article.service.ArticleService;
import project.demo.article.service.CommentService;
import project.demo.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class CommetController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final ArticleService articleService;
    /// 댓글 작성
    @PostMapping("/{articleId}/comment")
    public ResponseEntity<?> insertComment(@PathVariable long articleId, @RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = commentService.insertComment(articleId, commentRequestDTO);
        return ResponseEntity.ok("success : " + comment.getId());
    }
    /// 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteCommnet(@PathVariable long id) {
        Comment comment = commentService.deleteComment(id);
        return ResponseEntity.ok("success : " + comment.getId());
    }
    /// 대댓글 작성
    @PostMapping("/{parentId}/insertchild")
    public ResponseEntity<String> insertChildren(@PathVariable long parentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        Comment comment = commentService.insertChildren(parentId, commentRequestDTO);
        return ResponseEntity.ok("success : " + comment.getId());
    }
}
