package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.article.dto.CommentDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.Comment;
import project.demo.article.repository.CommentRepository;
import project.demo.security.resultdata.RsData;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    /// 댓글 작성
    @Transactional
    public ResponseEntity<String> insertComment(Article article, String content, String nickname) {
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .author(nickname)
                .build();
        commentRepository.save(comment);
        article.getComment().add(comment);
        return ResponseEntity.ok("success");
    }
    /// 댓글 삭제
    @Transactional
    public ResponseEntity<String> deleteCommnet(long id, Article article, String nickname) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if(comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        if(!comment.getAuthor().equals(nickname)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        commentRepository.delete(comment);

        return ResponseEntity.ok("success");
    }

    public List<CommentDTO> getCommentList(long id) {
        List<Comment> commentList = commentRepository.getCommentsByArticle_Id(id).orElse(null);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentDTOList.add(new CommentDTO(comment));
        }
        return commentDTOList;
    }
}
