package project.demo.article.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import project.demo.article.entity.Article;
import project.demo.article.entity.ArticleLike;
import project.demo.article.repository.ArticleLikeRepository;
import project.demo.article.repository.ArticleRepository;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;
import project.demo.member.service.MemberService;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.UnauthorizedException;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    /// 좋아요
    @Transactional
    public ResponseEntity<?> like(long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) throw new BadRequestException("article not found");
        Member member = memberService.findByusername(memberService.getCurrentUsername());
        if(member == null) throw new UnauthorizedException("required login");

        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(id, member.getId()).orElse(null);
        if(articleLike == null) {
            articleLike = ArticleLike.builder()
                    .article(article)
                    .member(member)
                    .build();
            article.setLikes(article.getLikes() + 1);
            articleLikeRepository.save(articleLike);
            return ResponseEntity.ok("좋아요! 현재 좋아요 수: " + article.getLikes());
        }
        else{
            articleLikeRepository.delete(articleLike);
            article.setLikes(article.getLikes() - 1);
            return ResponseEntity.ok("좋아요 취소, 현재 좋아요 수: " + article.getLikes());
        }
    }
    /// 좋아요 확인
    public boolean liked(Article article) {
        Member member = memberService.findByusername(memberService.getCurrentUsername());
        if(member == null) {return false;}
        ArticleLike articleLike = articleLikeRepository.findByArticleIdAndMemberId(article.getId(), member.getId()).orElse(null);
        return articleLike != null;
    }
}
