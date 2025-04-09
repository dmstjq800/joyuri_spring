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
import project.demo.security.exeption.customexception.ForbiddenException;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.NotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    /// 댓글 작성
    @Transactional
    public Comment insertComment(long id, CommentDTO commentDTO) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new NotFoundException("Article not found"));
        if(commentDTO.getContent() == null || commentDTO.getContent().isEmpty()) throw new BadRequestException("Comment content is null");
        Member member = memberService.getCurrentMember();
        Comment comment = Comment.builder()
                .article(article)
                .content(commentDTO.getContent())
                .author(member)
                .build();
        commentRepository.save(comment);
        return comment;
    }
    /// 댓글 삭제
    @Transactional
    public Comment deleteCommnet(CommentDTO commentDTO) {
        Member member = memberService.getCurrentMember();
        Comment comment = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> new NotFoundException("Comment not found"));
        if(!comment.getAuthor().equals(member) && !member.getRoles().contains("ROLE_ADMIN")) throw new ForbiddenException("Forbidden");
        commentRepository.delete(comment);
        return comment;
    }
    /// 대댓글 작성
    public Comment insertChildren(CommentDTO commentDTO) {
        Comment parent = commentRepository.findById(commentDTO.getId()).orElseThrow(() -> new NotFoundException("Parent not found"));
        if(parent.getParent() != null) throw new BadRequestException("This comment already has a parent");
        Article article = parent.getArticle();
        Member member = memberService.getCurrentMember();
        Comment children = Comment.builder()
                .parent(parent)
                .content(commentDTO.getContent())
                .author(member)
                .article(article)
                .build();
        commentRepository.save(children);
        return children;
    }
    /// 댓글 리스트
    public List<CommentDTO> getCommentList(long id) {
        List<Comment> commentList = commentRepository.findCommentByArticleId(id).orElse(null);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentDTOList.add(new CommentDTO(comment));
        }
        return commentDTOList;
    }
    /// 대댓글 리스트
    public List<CommentDTO> findByParentId(long id) {
        List<Comment> comments = commentRepository.findByParentId(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        return comments.stream().map(CommentDTO::new).collect(Collectors.toList());
    }
}
