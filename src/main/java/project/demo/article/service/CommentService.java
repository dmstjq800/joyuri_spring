package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.article.dto.CommentDTO;
import project.demo.article.entity.Article;
import project.demo.article.entity.Comment;
import project.demo.article.repository.ArticleRepository;
import project.demo.article.repository.CommentRepository;
import project.demo.member.entity.Member;
import project.demo.member.service.MemberService;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    /// 댓글 작성
    @Transactional
    public ResponseEntity<String> insertComment(long id, CommentDTO commentDTO) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
        Member member = memberService.getCurrentMember();
        if (member == null) {return ResponseEntity.status(HttpStatus.FORBIDDEN).build();}
        Comment comment = Comment.builder()
                .article(article)
                .content(commentDTO.getContent())
                .author(member)
                .build();
        commentRepository.save(comment);
        return ResponseEntity.ok("success");
    }
    /// 댓글 삭제
    @Transactional
    public ResponseEntity<String> deleteCommnet(CommentDTO commentDTO) {
        Member member = memberService.getCurrentMember();
        Comment comment = commentRepository.findById(commentDTO.getId()).orElse(null);
        if(comment == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("commnet not found");
        if(!comment.getAuthor().equals(member) && !member.getRoles().contains("ROLE_ADMIN")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized");
        commentRepository.delete(comment);
        return ResponseEntity.ok("success");
    }
    /// 댓글 DTO
    public List<CommentDTO> getCommentList(long id) {
        List<Comment> commentList = commentRepository.findCommentByArticleIdAndParentIsNull(id).orElse(null);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentDTOList.add(new CommentDTO(comment));
        }
        return commentDTOList;
    }
    /// 대댓글 작성
    public ResponseEntity<String> insertChildren(CommentDTO commentDTO) {
        Comment parent = commentRepository.findById(commentDTO.getId()).orElse(null);
        if(parent == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("parent not found");
        if(parent.getParent() != null) return ResponseEntity.status(HttpStatus.CONFLICT).body("This is child of comment, number is " + parent.getId());
        Article article = parent.getArticle();
        Member member = memberService.getCurrentMember();
        if(member == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("required login");
        Comment children = Comment.builder()
                .parent(parent)
                .content(commentDTO.getContent())
                .author(member)
                .article(article)
                .build();
        commentRepository.save(children);
        return ResponseEntity.ok("success");
    }
    /// 대댓글 DTO
    public ResponseEntity<?> findByParentId(long id) {
        List<Comment> comments = commentRepository.findByParentId(id).orElse(null);
        if(comments.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("commnet not found");
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : comments) {
            commentDTOList.add(new CommentDTO(comment));
        }
        return ResponseEntity.ok(commentDTOList);
    }
}
