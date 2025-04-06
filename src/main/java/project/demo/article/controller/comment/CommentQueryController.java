package project.demo.article.controller.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.article.dto.CommentDTO;
import project.demo.article.service.ArticleService;
import project.demo.article.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class CommentQueryController {
    private CommentService commentService;
    private ArticleService articleService;

    /// 댓글 리스트
    @GetMapping("/{id}/commentList")
    public ResponseEntity<List<CommentDTO>> commentList(@PathVariable long id) {
        if(articleService.findById(id) == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(commentService.getCommentList(id));
    }
    /// 대댓글 리스트
    @GetMapping("/getChild")
    public ResponseEntity<?> childList(String id) {
        return ResponseEntity.ok(commentService.findByParentId(Long.parseLong(id)));
    }
}
