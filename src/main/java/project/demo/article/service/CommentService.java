package project.demo.article.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import project.demo.article.dto.CommentRequestDTO;
import project.demo.article.dto.CommentResponseDTO;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberService memberService;

    /// 댓글 작성
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Comment insertComment(long articleId, CommentRequestDTO commentRequestDTO) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new NotFoundException("Article not found"));
        if(commentRequestDTO.getContent() == null || commentRequestDTO.getContent().isEmpty()) throw new BadRequestException("Comment content is null");
        Member member = memberService.getCurrentMember();
        Comment comment = Comment.builder()
                .article(article)
                .content(commentRequestDTO.getContent())
                .author(member)
                .build();
        commentRepository.save(comment);
        return comment;
    }
    /// 댓글 삭제
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public Comment deleteComment(long id) {
        Member member = memberService.getCurrentMember();
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        if(!comment.getAuthor().equals(member) && !member.getRoles().contains("ROLE_ADMIN")) throw new ForbiddenException("Forbidden");
        commentRepository.delete(comment);
        return comment;
    }
    /// 대댓글 작성
    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Comment insertChildren(long parentId, CommentRequestDTO commentRequestDTO) {
        Comment parent = commentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("Parent not found"));
        if(parent.getParent() != null) throw new BadRequestException("This comment already has a parent");
        Article article = parent.getArticle();
        Member member = memberService.getCurrentMember();
        Comment children = Comment.builder()
                .parent(parent)
                .content(commentRequestDTO.getContent())
                .author(member)
                .article(article)
                .build();
        commentRepository.save(children);
        return children;
    }
    /// 댓글 리스트
    public List<CommentResponseDTO> getCommentList(long id) {
        List<Comment> commentList = commentRepository.findCommentByArticleId(id).orElse(null);
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDTOList.add(new CommentResponseDTO(comment));
        }
        return commentResponseDTOList;
    }
    /// 대댓글 리스트
    public List<CommentResponseDTO> findByParentId(long id) {
        List<Comment> comments = commentRepository.findByParentId(id).orElseThrow(() -> new NotFoundException("Comment not found"));
        return comments.stream().map(CommentResponseDTO::new).collect(Collectors.toList());
    }
}
